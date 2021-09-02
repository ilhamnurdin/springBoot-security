package com.SpringSecurityOAuth2JWT.springbootoauth2jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
public class ConfigOAuth2 extends AuthorizationServerConfigurerAdapter {
    // change the clientId and clientSecret if ge  error in postmaan
    private String clientId = "test";
    private String clientSecret = "kuncinyaadlahasholatkawan";
    private String privateKey = "-----BEGIN RSA PRIVATE KEY-----\n" +
            "MIIEpAIBAAKCAQEAqoEUilIVrlN/9W7raUR9JQhI7bfz7F1gZ9ODy/DIDRAsQvhj\n" +
            "ELE5vJHR0j+KSxceOm1tKZ/0VuBq1t7VuQJC5s1xHWgHR1ViJH/kDgWAzl2gfpMA\n" +
            "ZzWeD++ZgCsRmkaZn7x/u5hP3QHP5lDluBIldv8vFlZfCAkaMu6Au+mkO+T60CSJ\n" +
            "N27capvgOCqBNjcJre0NcB5vevDVdtjmePQPCM1CCNhzvfkIfgB4Y0XSPv8a74ta\n" +
            "PmlytfoV4Y/IIY6mJPMGUoSfsYgupPzkDeIplH+bTnEze/fmDIAL8umAGE8U6375\n" +
            "4xDdVuVoRpTmW3ZJNvzg6l4zuMtvCPxk4VGIUQIDAQABAoIBADpC5TvmlJX+3kiF\n" +
            "YFeWGZUf4ZupMeZgeeLAedakLheYoSc5LNC1Xl87DJFlOOIzwhaA8b45dAqDoL5P\n" +
            "YoRXa3jmVnKP63zKhkCmmszXwI3DnGXj4U1jGuGjI7aSAO/QlAnrJVJ61z9TsTCo\n" +
            "D/b37GlfBT4CwXJDefpRWRfc3EvCgdHnPm4byAcGodte1JALexlCnNsdI8hKlslk\n" +
            "BJMeHEWECtlwe1nNXVV8bgnKOGfDGglHKJnpBhG01QW/LQ+Ruym+obsh4mykN13O\n" +
            "eSe08a/1/4GVOp5trWsr1y6D8Aq+YbFAeAn5CvHFsOeS7yOzQw5BMm1EeDriMJfE\n" +
            "1+73/GECgYEA0RmFfQkJvUzUpLeX44hG7yvqVUS5yUb/9L9D1Pj3DfArHLfzDtJD\n" +
            "li8AxVS+FpWMaExQdnQyjLLbBTdEU42U6/6XNmziZGTaDGzmBZWeD6GvLky95YTq\n" +
            "CY7sIbseCmrjGX5BTqHQ9XYb4Ebml+eyG8GTkqz4obTzgzKlq8HHus0CgYEA0L9p\n" +
            "VoRhnzXBGFDNsgSLIFRbIV3UXyxYmJtKjq+sWYNDmhuEmt/y6bDbNxEWgw16/92K\n" +
            "YwMC1sy+AJAXVzxmbwEYB0wiVu9p4wB82d9JaZ3bnK5silnR8DwVP5XXaSnWv6S6\n" +
            "a0dF7mKBkqN5pOnxJBcY9ND4Cce7KkzYc+U3C5UCgYEAxQX/OemiO7SBSROVEtf9\n" +
            "vq7qhp3TUUyNMNhn2e0dHQ0IEm9hnr2Q4Zo4Z7eJxU30lvIRfwvN9bUxmqCQyQnH\n" +
            "IPcrFCVUzLQaoUqbEvf/j0sR/dZhuDqdK5R3+vCNRdOhWQwWDTl7403+xj0IWmn+\n" +
            "8RhdLP++5t/R8/VtFYHp6KECgYB6nsWg1biGjkv0HZsGpBaZkhPKANT8Zw9P7YCZ\n" +
            "WmYyWqH+R4XYeA636XSL31TK/MMww5FD4gPr627846diTg+ZyxmzR6ywRoh7mNC+\n" +
            "JqR4Dwtu+SK3DlX8+T4+EKXtl28XOgtrcv2IL4MPgUm2btmAqrS1Mn9RN2shSOyo\n" +
            "/48bFQKBgQCMussIuGJpPUyiFqRn7S9bL2naE4ymd3NqMcXHVAz/wsJgCESKXw5s\n" +
            "jHVJCiduzpdzE/cDWpzCx+ywqZzhb6OvUr0CFL7X0QtHV/diX2p0Chja1WK4PdlQ\n" +
            "WaLb87AVaaMnSmtJXPg1ydDXlK46zAhH8f5cZQBWjL4RfzXBE6aX+g==\n" +
            "-----END RSA PRIVATE KEY-----";
    private String publicKey = "-----BEGIN PUBLIC KEY-----\n" +
            "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAqoEUilIVrlN/9W7raUR9\n" +
            "JQhI7bfz7F1gZ9ODy/DIDRAsQvhjELE5vJHR0j+KSxceOm1tKZ/0VuBq1t7VuQJC\n" +
            "5s1xHWgHR1ViJH/kDgWAzl2gfpMAZzWeD++ZgCsRmkaZn7x/u5hP3QHP5lDluBIl\n" +
            "dv8vFlZfCAkaMu6Au+mkO+T60CSJN27capvgOCqBNjcJre0NcB5vevDVdtjmePQP\n" +
            "CM1CCNhzvfkIfgB4Y0XSPv8a74taPmlytfoV4Y/IIY6mJPMGUoSfsYgupPzkDeIp\n" +
            "lH+bTnEze/fmDIAL8umAGE8U63754xDdVuVoRpTmW3ZJNvzg6l4zuMtvCPxk4VGI\n" +
            "UQIDAQAB\n" +
            "-----END PUBLIC KEY-----";

    @Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Bean
    public JwtAccessTokenConverter tokenEnhancer() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(privateKey);
        converter.setVerifierKey(publicKey);
        return converter;
    }

    @Bean
    public JwtTokenStore tokenStore() {
        return new JwtTokenStore(tokenEnhancer());
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager).tokenStore(tokenStore())
                .accessTokenConverter(tokenEnhancer());
    }
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
    }
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory().withClient(clientId).secret(passwordEncoder.encode(clientSecret)).scopes("read", "write")
                .authorizedGrantTypes("password", "refresh_token").accessTokenValiditySeconds(20000)
                .refreshTokenValiditySeconds(20000);

    }
}
