/**
 * 通过http协议与服务器进行交互
 */
const server = {
  /**
   * 向服务器发送get请求，请求数据参数拼接在url中，返回数据皆为json格式字符串
   * @param url
   * @param params
   * @returns {Promise<>}
   */
  get(url,params){
      const a = useState();
    if (params) {
      let paramsArray = [];
      //拼接参数
      Object.keys(params).forEach(key =>
        paramsArray.push(key + '=' + encodeURI(params[key].toString())));

      if (paramsArray.length > 0) {
        if (url.search(/\?/) === -1) {
          url += '?' + paramsArray.join('&');
        } else {
          url += '&' + paramsArray.join('&');
        }
      }
    }
    return new Promise((resolve,reject) => {
        fetch(url)
            .then(res => res.json())
            .then(data => resolve(data))
            .catch(err => reject(err))
    });

  },
  /**
   * 向服务器发送post请求，请求数据与返回数据皆为json格式字符串
   * @param url
   * @param data
   * @returns {Promise<>}
   */
  post(url, data){
    return new Promise((resolve,reject) => {
      fetch(url, {
        method: 'POST',
        headers: {
          'Content-type': 'application/json',
        },
        body: JSON.stringify(data),
      })
          .then(res => res.json())
          .then(data => resolve(data))
          .catch(err => reject(err))
    });
  },
  /**
   * 向服务器发送put请求，请求数据与返回数据皆为json格式字符串
   * @param url
   * @param data
   * @returns {Promise<>}
   */
  put(url, data){
    return new Promise((resolve,reject) => {
      fetch(url, {
        method: 'PUT',
        headers: {
          'Content-type': 'application/json'
        },
        body: JSON.stringify(data)
      })
          .then(res => res.json())
          .then(data => resolve(data))
          .catch(err => reject(err))

    })
  },
  /**
   * 向服务器发送delete请求，请求数据与返回数据皆为json格式字符串
   * @param url
   * @param data
   * @returns {Promise<>}
   */
  delete(url, data){
    return new Promise( (resolve,reject) => {
      fetch(url, {
        method: 'DELETE',
        headers: {
          'Content-type': 'application/json'
        },
        body: JSON.stringify(data)
      })
          .then(res => res.json())
          .then(data => resolve(data))
          .catch(err => reject(err))
    })
  }
};

