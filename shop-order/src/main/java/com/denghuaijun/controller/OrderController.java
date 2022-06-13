package com.denghuaijun.controller;

import com.alibaba.fastjson.JSON;
import com.denghuaijun.entity.Order;
import com.denghuaijun.entity.Product;
import com.denghuaijun.entity.User;
import com.denghuaijun.fegins.ProductFeginSevice;
import com.denghuaijun.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Random;

/**
 * OrderController
 *
 * @author denghuaijun@eversec.cn
 * @date 2022/5/17 11:27
 * @Description 订单API
 */
@RestController
@Slf4j
public class OrderController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private OrderService orderService;

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private ProductFeginSevice feginSevice;

    //准备买1件商品
    @GetMapping("/order/prod/{pid}/{uid}")
    public Order order (@PathVariable("pid") Integer pid,@PathVariable("uid") Integer uid){
        log.info("》》》客户下单，这时候调用商品微服务查询商品信息");
        //通过restTemplate调用商品微服务
        ResponseEntity<Product> responseEntity = restTemplate.getForEntity("http://localhost:8081/product/" + pid, Product.class);
        Product product = responseEntity.getBody();
        log.info("》》》客户下单，这时候调用商品微服务查询商品信息:{}", JSON.toJSONString(product));
        //通过restTemplate调用用户微服务
        User user = restTemplate.getForObject("http://localhost:8071/user/" + uid, User.class);
        log.info("下单的用户信息:",JSON.toJSONString(user));
        Order order = new Order();
        order.setUid(user.getUid());
        order.setUsername(user.getUsername());
        orderService.save(order);
        return order;
    }

    /**
     * 测试获取nacos注册中心的服务示例URL及端口
     * @param pid
     * @return
     */
    @GetMapping("/getDisCovery/product/{pid}")
    public Order getDisCovery (@PathVariable("pid") Integer pid){
        log.info("》》》客户下单，这时候调用商品微服务查询商品信息");
        //通过restTemplate调用商品微服务
        //获取商品服务的服务示例
        Product product = null;
        List<ServiceInstance> serviceInstanceList = discoveryClient.getInstances("service-product");
        if (!CollectionUtils.isEmpty(serviceInstanceList)){
            ServiceInstance serviceInstance = serviceInstanceList.get(0);
            String url = serviceInstance.getHost()+":"+serviceInstance.getPort();
            product = restTemplate.getForObject("http://"+url+"/product/"+pid,Product.class);
        }
        log.info("》》》客户下单，这时候调用商品微服务查询商品信息:{}", JSON.toJSONString(product));
        Order order = new Order();
        order.setUid(1);
        order.setUsername("test");
        order.setPid(product.getPid());
        orderService.save(order);
        return order;
    }

    /**
     * 测试获取nacos注册中心的服务示例URL及端口.,并使用java程序来实现客户端的负载均衡
     * @param pid
     * @return
     */
    @GetMapping("/getDisCovery/loadBalance/{pid}")
    public Order getDisCoveryLoadBalance (@PathVariable("pid") Integer pid){
        log.info("》》》客户下单，这时候调用商品微服务查询商品信息");
        //通过restTemplate调用商品微服务
        //获取商品服务的服务示例
        Product product = null;
        List<ServiceInstance> serviceInstanceList = discoveryClient.getInstances("service-product");
        if (!CollectionUtils.isEmpty(serviceInstanceList)){
            int index = new Random().nextInt(serviceInstanceList.size());
            ServiceInstance serviceInstance = serviceInstanceList.get(index);
            String url = serviceInstance.getHost()+":"+serviceInstance.getPort();
            log.info("从nacos中获取的商品服务端信息URL：{}",url);
            product = restTemplate.getForObject("http://"+url+"/product/"+pid,Product.class);
        }
        log.info("》》》客户下单，这时候调用商品微服务查询商品信息:{}", JSON.toJSONString(product));
        Order order = new Order();
        order.setUid(1);
        order.setUsername("test");
        order.setPid(product.getPid());
        orderService.save(order);
        return order;
    }


    /**
     * 测试ribbon的负载均衡
     * @param pid
     * @return
     */
    @GetMapping("/getDisCovery/ribbon/{pid}")
    public Order getOrderByRibbon (@PathVariable("pid") Integer pid){
        log.info("》》》客户下单，这时候调用商品微服务查询商品信息");
        //通过restTemplate调用商品微服务
        //获取商品服务的服务示例
        String serviceName ="service-product";
        Product product =product = restTemplate.getForObject("http://"+serviceName+"/product/"+pid,Product.class);
        log.info("》》》客户下单，这时候调用商品微服务查询商品信息:{}", JSON.toJSONString(product));
        Order order = new Order();
        order.setUid(1);
        order.setUsername("test22");
        order.setPid(product.getPid());
        orderService.save(order);
        return order;
    }

    /**
     * 测试使用fegin接口实现负载均衡，内含ribbon了
     * @param pid
     * @return
     */
    @GetMapping("/getDisCovery/fegin/{pid}")
    public Order getOrderByFegin (@PathVariable("pid") Integer pid){
        log.info("》》》客户下单，这时候调用商品微服务查询商品信息");
        //通过restTemplate调用商品微服务
        //获取商品服务的服务示例
        String serviceName ="service-product";
        Product product =feginSevice.getProduct(pid);
        if (product.getPid() ==-1){
            Order order = new Order();
            order.setPname("下单失败！");
            return order;
        }
        log.info("》》》客户下单，这时候调用商品微服务查询商品信息:{}", JSON.toJSONString(product));
        Order order = new Order();
        order.setUid(1);
        order.setUsername("test-fegin");
        order.setPid(product.getPid());
        order.setPname(product.getPname());
        orderService.save(order);
        return order;
    }

    public static void main(String[] args) throws InterruptedException {
        int index = new Random().nextInt(2);
        while(true){
            Thread.sleep(1000);
            System.out.println(index);
        }
    }

}
