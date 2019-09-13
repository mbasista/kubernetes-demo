package pl.demo.noteservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;

@Configuration
public class DbServiceConfig {

    @Value("${dbservice_uri}")
    private String defaultUri;

    @Bean
    public DbServiceClient dbServiceClient(Jaxb2Marshaller marshaller) {
        DbServiceClient client = new DbServiceClient();
        ClientInterceptor[] interceptors = {new SoapFaultInterceptor()};
        client.setInterceptors(interceptors);
        client.setDefaultUri(defaultUri);
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        return client;
    }

    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("pl.demo.noteservice.generated.dbservice.wsdl");
        return marshaller;
    }
}
