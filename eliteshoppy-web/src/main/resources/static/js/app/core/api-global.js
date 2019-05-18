/* API URLs */
/* Authentication-Service URLS */
var REQUEST_TOKEN_URL = API_URL + "/auth-service/oauth/token";
var FETCH_PRINCIPAL_USER_URL = API_URL + "/auth-service/useraccount/me";
var USER_REGISTER_URL = API_URL + "/auth-service/useraccount";
var USER_UPDATE_URL = API_URL + "/auth-service/useraccount";

/* Product-Service URLS */
var PRODUCT_LOADBYOWNER = API_URL + "/product-service/product/owner/";
var CREATE_PRODUCT = API_URL + "/product-service/product";
var GET_PRODUCT = API_URL + "/product-service/product/";
var UPDATE_PRODUCT = API_URL + "/product-service/product";
var DELETE_PRODUCT = API_URL + "/product-service/product/";
var UPDATE_PRODUCT_STATUS = API_URL + "/product-service/product/status/";

/* Attribute URLS */
var GET_ALL_ATTRIBUTES = API_URL + "/product-service/attribute";
var CREATE_ATTRIBUTE = API_URL + "/product-service/attribute";
var DELETE_ATTRIBUTE = API_URL + "/product-service/attribute/";

/* Image URLS */
var UPLOAD_IMAGE = API_URL + "/product-service/image/upload";
var DOWNLOAD_IMAGE = API_URL + "/product-service/image/download/";
var GET_IMAGES = API_URL + "/product-service/image/";
var DELETE_IMAGE = API_URL + "/product-service/image/";

/* Error Messages */
var ERROR_MSG_SIGNIN = "Something went wrong while signing in! Please try again later.";
var ERROR_MSG_SIGNUP = "Something went wrong while signing up! Please try again later.";
var ERROR_MSG_INVALID_CRED = "Invalid email id or password!";
