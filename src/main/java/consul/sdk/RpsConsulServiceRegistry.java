package consul.sdk;

import com.ecwid.consul.v1.ConsulClient;
import org.springframework.cloud.consul.discovery.ConsulDiscoveryProperties;
import org.springframework.cloud.consul.discovery.HeartbeatProperties;
import org.springframework.cloud.consul.discovery.TtlScheduler;
import org.springframework.cloud.consul.serviceregistry.ConsulRegistration;
import org.springframework.cloud.consul.serviceregistry.ConsulServiceRegistry;

public class RpsConsulServiceRegistry extends ConsulServiceRegistry {
    public RpsConsulServiceRegistry(ConsulClient client, ConsulDiscoveryProperties properties, TtlScheduler ttlScheduler, HeartbeatProperties heartbeatProperties) {
        super(client, properties, ttlScheduler, heartbeatProperties);
    }

    @Override
    public void register(ConsulRegistration reg) {
        reg.getService().setId(reg.getService().getName() + ":" + reg.getService().getAddress() + ":" + reg.getService().getPort());
        super.register(reg);
    }
}
