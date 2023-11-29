package com.appfitgym.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
@EnableWebMvc
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig implements WebMvcConfigurer {



    private final String rememberMeKey;

    private final UserDetailsService userDetailsService;





    public SecurityConfig(@Value("${spring.remember.me.key}") String rememberMeKey, UserDetailsService userDetailsService){
        this.rememberMeKey = rememberMeKey;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public HiddenHttpMethodFilter hiddenHttpMethodFilter() {
        return new HiddenHttpMethodFilter();
    }



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable)

                .authorizeRequests(req ->
                        req.
                                requestMatchers("/resources/**", "/static/**","/","/users/login","users/register/**","/countries","/api/cities/**","https://unpkg.com/swiper@7/swiper-bundle.min.css","/users/login-error","/food/**", "/register/verifyEmail")
                                .permitAll().requestMatchers("/js/**", "/css/**", "/images/**", "/webfonts/**", "/jquery/**", "/bootstrap/**").permitAll()



                                .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                                .permitAll()

                                .requestMatchers("/error").permitAll()
                                .anyRequest().authenticated())
                .formLogin(formLogin ->
                        formLogin.loginPage("/users/login")
                                .usernameParameter("username")
                                .passwordParameter("password")
                                .defaultSuccessUrl("/", true)
                                .failureForwardUrl("/users/login-error")
                ).logout(logout ->
                        logout.logoutUrl("/users/logout")
                                .logoutSuccessUrl("/")
                                .invalidateHttpSession(true)
                                .logoutRequestMatcher(new AntPathRequestMatcher("/users/logout"))

                ).rememberMe(rememberMe ->
                        rememberMe
                                .userDetailsService(userDetailsService)
                                .key(rememberMeKey)
                                .rememberMeParameter("rememberme")
                                .rememberMeCookieName("rememberme")
                                .tokenValiditySeconds(60 * 60 * 24 * 14)
                ).exceptionHandling(exceptionHandling ->
                        exceptionHandling.accessDeniedPage("/403"));;



        return http.build();
    }



    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(createPasswordEncoder());
        return authProvider;
    }


    @Bean
    public PasswordEncoder createPasswordEncoder(){
        return  Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_8();
    }




    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/css/**", "/images/**", "/js/**","/bootstrap/**","/webfonts/**","/jquery/**", "/images/profile/**")
                .addResourceLocations("classpath:/static/css/", "classpath:/static/images/", "classpath:/static/js/", "classpath:/static/bootstrap/", "classpath:/static/webfonts/", "classpath:/static/jquery/", "classpath:/static/images/profile/");
    }




}




