/* eslint-disable */
import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import SuiVue from 'semantic-ui-vue'
import A from './components/A'
// import IdentityCheck from './components/Business/IdentityCheck'

var axios = require('axios'     )
axios.defaults.baseURL = 'http://106.12.213.102:8443/api'
// axios.defaults.baseURL = 'http://39.101.135.32:8443/api'
// axios.defaults.baseURL = 'http://localhost:8443/api'
Vue.prototype.$axios = axios
Vue.config.productionTip = false

Vue.use(SuiVue)
Vue.use(ElementUI)

/* eslint-disable no-new */
new Vue({
  el: '#app',
  render: h => h(App),
  router,
  store,
  components: { App },
  template: '<App/>'
})
