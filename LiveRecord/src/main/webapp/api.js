/**
 * 整合接口
 */

import axios from 'axios'
import { host, delData } from './config'
import weui from 'weui.js'
import qs from 'qs'

// 接口地址集合地址
const urlData = {
  // 获取注册短信验证码 GET
//  getregcode: '/LiveApi/mp/getregcode',
  getregcode: '/LiveApi/app/getregcode',
  // 用户注册
//  mpregister: '/LiveApi/mp/mpregister',
  mpregister: '/LiveApi/app/register',
  // 用户登录
//  mplogin: '/LiveApi/mp/mplogin',
  mplogin: '/LiveApi/app/login',
  // 课程列表接口
  courselist: '/LiveApi/app/courselist',
  // 记录意向客户接口
//  addcustomer: '/LiveApi/mp/addcustomer',
  addcustomer: '/LiveApi/app/addcustomer',
  // 获取登录短信验证码
//  getlogincode: '/LiveApi/mp/getlogincode',
  getlogincode: '/LiveApi/app/getlogincode',
  // 点赞接口
  praise: '/LiveApi/app/praise',
  // 课程详情接口
  coursedetail: '/LiveApi/app/coursedetail',
  // 课程目录接口
  coursedirectory: '/LiveApi/app/coursedirectory',
  // 课程评价列表接口
  coursecomment: '/LiveApi/app/coursecomment',
  // 获取vip套餐与代金券列表接口
//  vippackage: '/LiveApi/mp/vippackage',
  vippackage: '/LiveApi/app/vippackage',
  // 创建vip订单接口
//  createviporder: '/LiveApi/mp/createviporder',
  createviporder: '/LiveApi/app/createviporder',
  // 订单支付接口
//  payment: '/LiveApi/mp/payment',
  payment: '/LiveApi/app/payment',
  // 获取重置密码短信验证码
//  getrepwdcode: '/LiveApi/mp/getrepwdcode',
  getrepwdcode: '/LiveApi/app/getrepwdcode',
  // 用户重置密码
//  resetpwd: '/LiveApi/mp/resetpwd',
  resetpwd: '/LiveApi/app/resetpwd',
  // 课程视频播放量+1
  videohitsinc: '/LiveApi/app/videohitsinc',
  // 首页课程列表
  indexcourselist: '/LiveApi/mp/indexcourselist',
  // 首页轮播图列表
  bannerlist: '/LiveApi/mp/bannerlist',
  // 获取我的代金券列表接口
//  cashcoupon: '/LiveApi/mp/cashcoupon',
  cashcoupon: '/LiveApi/app/cashcoupon',
  // 首页精彩推荐logo接口
  indexlivelogo: '/LiveApi/app/indexlivelogo',
  // 获取名师推荐列表
  recommendteacher: '/LiveApi/app/indexteacher',
  test: '/api/clockPunch/test',
  // 资讯模块列表
//  newsmodellist: '/LiveApi/mp/newsmodellist',
  newsmodellist: '/LiveApi/app/newsmodellist',
  // 资讯列表
//  newslist: '/LiveApi/mp/newslist',
  newslist: '/LiveApi/app/newslist',
  // 咨讯评论列表
//  newscomment: '/LiveApi/mp/newscomment',
  newscomment: '/LiveApi/app/newscomment',
  // 咨讯详情
//  newsdetail: '/LiveApi/mp/newsdetail',
  newsdetail: '/LiveApi/app/newsdetail',
  // vip策略咨讯列表
//  vipnewslist: '/LiveApi/mp/vipnewslist',
  vipnewslist: '/LiveApi/app/vipnewslist',
  // vip策略咨讯详情
//  vipnewsdetail: '/LiveApi/mp/vipnewsdetail',
  vipnewsdetail: '/LiveApi/app/vipnewsdetail',
  // 礼物列表
  goodslist: '/LiveApi/app/goodslist',
  paygoodsorder: '/LiveApi/app/paygoodsorder'
}

// Post接口方法封装
export function getPostData(name, m, loadFlag) {
  let url = host + urlData[name];
  if (!loadFlag) {
    var loading = weui.loading('加载中……', {
      className: 'custom-classname'
    });
  }
  return delData(m).then(data => {
    let qsdata = qs.stringify(data)
    return axios.post(url, qsdata).then(r => {
      if (!loadFlag) {
        loading.hide(function () {
          console.log('`loading` has been hidden');
        });
      }
      if (r.data.code == '200') {
        // alert(JSON.stringify(r.data))
        return Promise.resolve(r.data)
      } else {
        // console.log(r.data.msg)
        if (r.data.msg) {
          weui.topTips(r.data.msg)
        } else {
          weui.topTips('出错了')
        }
        return Promise.reject(r.data)
      }
    })
  })
}

// get接口方法封装
export function getGetData(name, m, loadFlag) {
  let url = host + urlData[name];
  // if(name == 'coursecomment'){
  //   url = 'https://www.easy-mock.com/mock/5a24b4fb1b839f3936cd3db4/raisePig/api' + urlData[name];
  // }
  if (!loadFlag) {
    var loading = weui.loading('加载中……', {
      className: 'custom-classname'
    });
  }
  return delData(m).then(data => {
    return axios.get(url, { params: data })
      .then(r => {
        if (!loadFlag) {
          loading.hide(function () {
            console.log('`loading` has been hidden');
          });
        }
        console.log(r.data.code)
        if (r.data.code == '200') {
          return Promise.resolve(r);
        } else {
          console.log(r.data.msg)
          weui.topTips(r.data.msg)
          if (r.data.msg) {
            weui.topTips(r.data.msg)
          } else {
            weui.topTips('出错了')
          }
          return Promise.reject(r.data)
        }
      })
  })
}