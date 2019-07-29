package consul.sdk;

import com.alibaba.fastjson.JSON;
import consul.sdk.dto.JsonRpcDto;
import consul.sdk.exception.ConsulInvokeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;


public class ServerInvoke {
    @Autowired
    private LoadBalancerClient loadBalancer;
    @Autowired
    private DiscoveryClient discoveryClient;

    public String call(String serverName, String serviceName, String actionName, String version, Object arg) throws Exception {
        if (StringUtils.isEmpty(serverName)) {
            throw new ConsulInvokeException("1002", "请输入serverName");
        }
        if (StringUtils.isEmpty(serviceName)) {
            throw new ConsulInvokeException("1002", "请输入serviceName");
        }
        if (StringUtils.isEmpty(serverName)) {
            throw new ConsulInvokeException("1002", "请输入actionName");
        }
        ServiceInstance serviceInstance = discover(serverName);
        if (serviceInstance == null) {
            throw new ConsulInvokeException("1001", "无可用服务");
        }
        String url = serviceInstance.getUri().toString();
        if (serviceInstance.getMetadata() != null) {
            if (StringUtils.isEmpty(serviceInstance.getMetadata().get("ContextPath"))) {
                url += serviceInstance.getMetadata().get("contextPath");
            }
        }
        url += "/" + serviceName;
        JsonRpcDto param = getParam(actionName, arg);
        return HttpUtil.post(url, JSON.toJSONString(param));
    }

    /**
     * 获取所有服务
     *
     * @return
     */
    private List<ServiceInstance> services(String serverName) {
        return discoveryClient.getInstances(serverName);
    }

    /**
     * 从所有服务中选择一个服务（轮询）
     */
    private ServiceInstance discover(String serverName) {
        return loadBalancer.choose(serverName);
    }

    /**
     * 转换为jsonrpc 协议的参数
     *
     * @param methodName
     * @param arg
     * @return
     */
    private JsonRpcDto getParam(String methodName, Object arg) {
        JsonRpcDto jsonRpcDto = new JsonRpcDto();
        jsonRpcDto.setId("1");
        jsonRpcDto.setJsonRpc("2.0");
        jsonRpcDto.setMethod(methodName);
        jsonRpcDto.setParams(new ArrayList<Object>() {{
            if (arg != null) {
                add(arg);
            }
        }});
        return jsonRpcDto;
    }
}
