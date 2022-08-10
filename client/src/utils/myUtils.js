import jwt_decode from 'jwt-decode';
let MyUtils = {
    avatarUrl(url) {
        const urlPrefix = "http://localhost:8080/api/avatar/getAvatar?url="
        return urlPrefix + url;
    },
    uploadAvatarUrl() {
        const url = "http://localhost:8080/api/avatar/avatarPost"
        return url
    },
    fillLocalStorage(token) {
        const jwt = jwt_decode(token)
        localStorage.setItem('token', token);
        localStorage.setItem('isLogin', true);
        localStorage.setItem('name', jwt.name);
        localStorage.setItem('permission', jwt.permission);
        localStorage.setItem('identity', jwt.identity);
        localStorage.setItem('phone', jwt.phone.substring(0, 3) + '****' + jwt.phone.substring(7));
        localStorage.setItem('workNumb', jwt.workNumb);
        localStorage.setItem('avatarUrl', jwt.avatarUrl);
    }
}
export default MyUtils;