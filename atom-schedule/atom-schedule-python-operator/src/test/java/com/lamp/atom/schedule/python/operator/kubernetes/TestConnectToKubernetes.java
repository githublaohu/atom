package com.lamp.atom.schedule.python.operator.kubernetes;

import io.fabric8.kubernetes.api.model.ListOptions;
import io.fabric8.kubernetes.api.model.NamespaceList;
import io.fabric8.kubernetes.api.model.Pod;
import io.fabric8.kubernetes.client.Config;
import io.fabric8.kubernetes.client.ConfigBuilder;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import org.junit.Test;

public class TestConnectToKubernetes {

    @Test
    public void testConnect() throws Exception {
//        KubernetesClient client = new DefaultKubernetesClient("https://124.223.198.143:6443");

        Config config = new ConfigBuilder()
                .withMasterUrl("https://10.0.12.17:6443")
                .withOauthToken("eyJhbGciOiJSUzI1NiIsImtpZCI6Imp5T0xDTjJ4Y3FSN3R1V2lSaWJ3U29ycTFob1ZFMkdXbE4zTDdqWDRZVE0ifQ.eyJpc3MiOiJrdWJlcm5ldGVzL3NlcnZpY2VhY2NvdW50Iiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9uYW1lc3BhY2UiOiJrdWJlLXN5c3RlbSIsImt1YmVybmV0ZXMuaW8vc2VydmljZWFjY291bnQvc2VjcmV0Lm5hbWUiOiJhZG1pbi11c2VyLXRva2VuLThrejluIiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9zZXJ2aWNlLWFjY291bnQubmFtZSI6ImFkbWluLXVzZXIiLCJrdWJlcm5ldGVzLmlvL3NlcnZpY2VhY2NvdW50L3NlcnZpY2UtYWNjb3VudC51aWQiOiJlOGQ4MDNhMS03NDIzLTQ1YzQtODdmYy00ZDlkYzNmZDg0ZWEiLCJzdWIiOiJzeXN0ZW06c2VydmljZWFjY291bnQ6a3ViZS1zeXN0ZW06YWRtaW4tdXNlciJ9.TmoORqw0LKHYGOU02XvAUfd8VFeSE55-jQAdUBLvQwKQM8HE1Zkhecj3USCryk9BB1PRiE5H3Rkv6R0aaVxvGq0GeLcvhl5-g_mAuaCe4H7NXqqGBPeoLBznWzPc-TK1mcXaJFTto18M4S7xB815JosEj6MaoNyKbRX77z53TYMm6W6tjeGC_Yyc8GvjPmf8NBN-AHVWHUfkwD_-G94V1N2Fw-ByxBSOoDwqEfLDOFByatG8WwWvYv1Z1IKKj48DAiagtWPrgRfjIz05ao7qZ0mrsFAb666WDNAHRUjFTo2xzGVWbQ453prFv3j2sDwQsTa4aZHkVHqMFXeT9DPHeg")
                .withTrustCerts(true)
                .build();
        KubernetesClient client = new DefaultKubernetesClient(config);
        System.out.println(client);

        NamespaceList namespaceList = client.namespaces().list();
        namespaceList.getItems()
                .forEach(namespace ->
                        System.out.println(namespace.getMetadata().getName() + ":" + namespace.getStatus().getPhase()));

        // 查看Pod
        ListOptions options = new ListOptions();
        options.setLabelSelector("app=nginx");
        Pod nginx = client.pods().inNamespace("pkslow")
                .list(options)
                .getItems()
                .get(0);
        System.out.println(nginx);
    }

}
