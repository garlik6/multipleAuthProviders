## Authentication steps:
1) When user logs in first time (or after token expiration) he receives an OTP (OTP -one time password transferred via email, SMS or any other mean different from the one being used for client-server communication) 
2) Client includes received OTP in the next request, and gets a response with a token(Simple UUID in this case) 
3) Client can use this token to access secured endpoints until it expires (or request comes from different IP address, fraudulent or malicious activity is detected  or any other condition)
## Features
-  Whole Spring security auth flow customization(custom filters, providers, Authentication objects)
- multiple auth providers
- component based config(in leu of deprecated [WebSecurityConfigurerAdapter](https://spring.io/blog/2022/02/21/spring-security-without-the-websecurityconfigureradapter))
![ArhitectureImg](https://user-images.githubusercontent.com/56256488/182094904-68c41a2a-75f0-4a8a-a1fc-92a1dbb38eca.svg)
