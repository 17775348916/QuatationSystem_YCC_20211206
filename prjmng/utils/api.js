import axios from 'axios'
import {Message} from 'element-ui'
import router from '../src/router'
// 相应拦截器
axios.interceptors.response.use(success => {
  if (success.data.code && success.data.code === '200') {
    // Message.success({message: success.data.msg})
  } else {
    Message.error({message: success.data.msg})
  }
  return success
}, error => {
  if (error.response.code === '504' || error.response.code === '404') {
    Message.error({message: '服务器的问题'})
  } else if (error.response.code === '403') {
    Message.error({message: '权限不足，请联系管理员!'})
  } else if (error.response.code === '401') {
    Message.error({message: '尚未登陆，请登录'})
    router.replace('/A')
  } else {
    if (error.response.data.msg) {
      Message.error({message: error.response.data.msg})
    } else {
      Message.error({message: '位置错误'})
    }
  }
})
let base = ''
// 传送json格式的post请求
export const postRequest = (url, params) => {
  return axios({
    method: 'post',
    url: `${base}${url}`,
    data: params
  })
}
