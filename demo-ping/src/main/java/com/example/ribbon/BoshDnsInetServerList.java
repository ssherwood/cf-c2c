package com.example.ribbon;

import com.netflix.client.config.CommonClientConfigKey;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractServerList;
import com.netflix.loadbalancer.Server;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * Converts Bosh DNS "apps.internal" routes to a list of IP addresses for
 * Ribbon load balancing.
 *
 */
public class BoshDnsInetServerList extends AbstractServerList<Server> {
    public static final String APPS_INTERNAL = "apps.internal";

    private IClientConfig clientConfig;

    /**
     * @param clientConfig
     */
    public BoshDnsInetServerList(IClientConfig clientConfig) {
        this.clientConfig = clientConfig;
    }

    @Override
    public List<Server> getInitialListOfServers() {
        return getUpdatedListOfServers();
    }

    @Override
    public List<Server> getUpdatedListOfServers() {
        System.out.println("called getUpdatedListOfServers");
        String listOfServers = clientConfig.get(CommonClientConfigKey.ListOfServers);
        return derive(listOfServers);
    }

    @Override
    public void initWithNiwsConfig(IClientConfig clientConfig) {
        this.clientConfig = clientConfig;
    }

    protected List<Server> derive(String value) {
        List<Server> serverList = new ArrayList<>();

        if (!isNullOrEmpty(value)) {
            for (String serverEntry : value.split(",")) {
                if (serverEntry.contains(APPS_INTERNAL)) {
                    String serverHost = serverEntry.trim();
                    String serverPort = null;

                    if (serverEntry.contains(":")) {
                        String[] splitAddr = serverEntry.trim().split(":");
                        serverHost = splitAddr[0].trim();
                        serverPort = splitAddr[1].trim();
                    }

                    try {
                        // try to resolve all IPs for this local route
                        InetAddress[] ips = InetAddress.getAllByName(serverHost);

                        for (InetAddress ip : ips) {
                            if (serverPort == null) {
                                serverList.add(new Server(ip.getHostAddress()));
                            } else {
                                serverList.add(new Server(ip.getHostAddress() + ":" + serverPort));
                            }
                        }
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    }
                } else {
                    serverList.add(new Server(serverEntry.trim()));
                }
            }
        }

        return serverList;
    }

    private boolean isNullOrEmpty(String value) {
        return value == null || value.isEmpty();
    }
}
