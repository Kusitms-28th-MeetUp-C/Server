## Code Convention 🍀 
[MeetUP C팀의 Code Convention 보러가기 ✔️](https://ultra-wallet-036.notion.site/Code-convention-34b0bd40253c430a95acc22e5260c29b?pvs=4)

<br>

## Git Convention 🍀
[MeetUP C팀의 Git Convention 보러가기 ✔️](https://ultra-wallet-036.notion.site/Git-flow-convention-a0777608d31d4b4db37097d0c64ac3bc?pvs=4)

<br>

## Architecture 🍀
<img width="829" alt="image" src="https://github.com/Kusitms-28th-MeetUp-C/Server/assets/97783148/8e70644c-a999-43e3-9e2a-011e522349d8">

<br>

## Main Server Foldering 📂
```
📂 src
┣ 📂 java.com.kusitms.mainservice
┃  ┣ 📂 domain
┃  ┃  ┣ 📂 sample
┃  ┃  ┃  ┣ 📂 controller
┃  ┃  ┃  ┣ 📂 dto
┃  ┃  ┃  ┃  ┣ 📂 request
┃  ┃  ┃  ┃  ┣ 📂 response
┃  ┃  ┃  ┣ 📂 service
┃  ┃  ┃  ┣ 📂 domain
┃  ┃  ┃  ┣ 📂 repository
┃  ┃  ┃  ┣ 📂 mongoRepository
┃  ┣ 📂 global
┃  ┃  ┣ 📂 common
┃  ┃  ┣ 📂 config
┃  ┃  ┃  ┣ 📂 auth
┃  ┃  ┣ 📂 error
┃  ┃  ┃  ┣ 📂 dto
┃  ┃  ┃  ┣ 📂 exception
┃  ┃  ┃  ┣ 📂 handler
┣ 📂 resources
┃  ┣ application.yml
┣ MainServiceApplication.class
```

<br>

## Socket Server Foldering 📂
```
📂 src
┣ 📂 java.com.kusitms.socketservice
┃  ┣ 📂 domain
┃  ┃  ┣ 📂 sample
┃  ┃  ┃  ┣ 📂 controller
┃  ┃  ┃  ┣ 📂 dto
┃  ┃  ┃  ┃  ┣ 📂 request
┃  ┃  ┃  ┃  ┣ 📂 response
┃  ┃  ┃  ┣ 📂 service
┃  ┃  ┃  ┣ 📂 domain
┃  ┃  ┃  ┣ 📂 repository
┃  ┣ 📂 global
┃  ┃  ┣ 📂 common
┃  ┃  ┃  ┣ AuthenticationInterceptor.class
┃  ┃  ┃  ┣ MessageSuccessCode.class
┃  ┃  ┃  ┣ MessageSuccessResponse.class
┃  ┃  ┣ 📂 config
┃  ┃  ┃  ┣ 📂 auth
┃  ┃  ┣ 📂 error
┃  ┃  ┃  ┣ 📂 dto
┃  ┃  ┃  ┣ 📂 httpException
┃  ┃  ┃  ┣ 📂 socketException
┃  ┃  ┃  ┣ 📂 handler
┣ 📂 resources
┃  ┣ application.yml
┣ SocketServiceApplication.class
```
