# prjmng

> A Vue.js project

## Build Setup

``` bash
# install dependencies
npm install

# serve with hot reload at localhost:8080
npm run dev

# build for production with minification
npm run build

# build for production and view the bundle analyzer report
npm run build --report

# run unit tests
npm run unit

# run e2e tests
npm run e2e

# run all tests
npm test
```

For a detailed explanation on how things work, check out the [guide](http://vuejs-templates.github.io/webpack/) and [docs for vue-loader](http://vuejs.github.io/vue-loader).


修改数据库配置（用户密码库名）：
src/main/resources/application.properties

后端 controller
HTTP requests are handled by a controller

“前后端分离” ，什么是 “单页面应用” 。

简单地说，`前后端分离` 的核心思想是
前端页面通过 ajax 调用后端的 restuful api 进行数据交互，
前后端分离的意思是前后端之间通过 RESTful API 传递 JSON 数据进行交流。
不同于 JSP 之类，后端是不涉及页面本身的内容的
而`单页面应用（single page web application，SPA）`，
就是只有一张页面，并在用户与应用程序交互时动态更新该页面的 Web 应用程序。

前端 vue
https://cn.vuejs.org/v2/guide/

安装 Node.js，访问官网 https://nodejs.org/en/，首页即可下载
在 cmd 中输入 node -v，检查是否安装成功
同时，npm 包也已经安装成功，可以输入 npm -v 查看版本号
输入 npm -g install npm ，将 npm 更新至最新版本。

npm install -g vue-cli 安装脚手架


ps:
前端请求默认发送到 http://localhost:8443/api
