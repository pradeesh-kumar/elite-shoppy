/* API URLs */
/* Authentication-Service URLS */
var REQUEST_TOKEN_URL = API_URL + "/auth-service/oauth/token";
var FETCH_PRINCIPAL_USER_URL = API_URL + "/auth-service/useraccount/me";
var USER_REGISTER_URL = API_URL + "/auth-service/useraccount";
var USER_UPDATE_URL = API_URL + "/auth-service/useraccount";

/* Product-Service URLS */
var PRODUCT_LOADBYOWNER = API_URL + "/product-service/product/owner/";

/* Error Messages */
var ERROR_MSG_SIGNIN = "Something went wrong while signing in! Please try again later.";
var ERROR_MSG_SIGNUP = "Something went wrong while signing up! Please try again later.";
var ERROR_MSG_INVALID_CRED = "Invalid email id or password!";