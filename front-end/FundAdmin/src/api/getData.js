import fetch from '@/config/fetch'

/**
 * 获取基金列表
 */

export const getFundList = data => fetch('/api/fund_signal/pageList', data, 'POST');


/**
 * 用户列表
 */

export const userList = data => fetch('/api/user/list', data, 'POST');

/**
 * 用户删除
 */

export const deleteUser = id => fetch('/api/user/delete/'+ id);

/**
 * 登陆
 */

export const login = data => fetch('/api/authenticate', data, 'POST');

/**
 * 删除基金
 */

export const deleteFund = fund_id => fetch('/api/fund_signal/delete/' + fund_id);

/**
 * 添加基金
 */

export const addFund = data => fetch('/api/fund_signal/createFundSignal', data, 'POST');

/**
 * 根据身份查询用户（创建基金）
 */

export const loadUsers = dep => fetch('/api/user/findByDepartment/'+ dep);

/**
 * 获取用户（添加交易账户）
 */

export const getUsers = data => fetch('/api/user/userComboList', data, 'POST');

/**
 * 添加发起人/资产管理人
 */

export const addPowerUser = data => fetch('/api/user/createPowerUser', data, 'POST');
/**
 * 添加系统用户
 */

export const addAdminUser = data => fetch('/api/user/save', data, 'POST');

/**
 * 出入金分页查询
 */

export const getRecord = data => fetch('/api/asset_detail/pageList', data, 'POST');

/**
 * 处理出入金申请
 */

export const handleSelect = data => fetch('/api/asset_detail/approveAssetDetail', data, 'POST');

/**
 * 获取交易账户列表
 */

export const getAccountList = data => fetch('/api/tlb_account/pageList', data, 'POST');

/**
 * 删除交易账户
 */

export const deleteAccount = id => fetch('/api/tlb_account/delete/' + id);

/**
 * 添加交易账户
 */

export const addAccount = data => fetch('/api/tlb_account/createTlbAccount', data, 'POST');

/**
 * 获取收款账号列表
 */

export const getReceiptList = () => fetch('/api/receivables/findAll');
/**
 * 新增收款账号
 */

export const addReceipt = data => fetch('/api/receivables/saveReceivables', data, 'POST');
/**
 * 更改收款账户
 */

export const handleReceiptSelect = id => fetch('/api/receivables/changFlag/'+id);
/**
 * 删除收款账户
 */

export const deleteReceipt = id => fetch('/api/receivables/delete/'+id);

/**
 * 获取轮播图
 */

export const getBanners = type => fetch('/api/advertisement/loadByType/'+type);

/**
 * 添加轮播图
 */

export const addBanner = data => fetch('/api/advertisement/saveAdvertisement', data, 'POST');

/**
 * 删除轮播图
 */

export const deleteBanner = id => fetch('/api/advertisement/delete/'+id);

/**
 * 添加/编辑协议
 */

export const addAgree = data => fetch('/api/protocol/saveProtocol', data, 'POST');


/**
 * 获取协议列表
 */

export const getAgreeList = () => fetch('/api/protocol/findList');

/**
 * 删除协议
 */

export const deleteAgree = id => fetch('/api/protocol/delete/' + id);

/**
 * 根据类型获取协议列表
 */

export const getAgreeByType = type => fetch('/api/protocol/getType/'+type);

/**
 * 获取基金详情（编辑基金）
 */

export const getFundDetail = id => fetch('/api/fund_signal/findOneDetail/' + id);

//=========================
/**
 * 退出
 */

export const signout = () => fetch('/admin/singout');

/**
 * 获取用户信息
 */

export const getAdminInfo = () => fetch('/admin/info');

/**
 * api请求量
 */

export const apiCount = date => fetch('/statis/api/' + date + '/count');

/**
 * 所有api请求量
 */

export const apiAllCount = () => fetch('/statis/api/count');


/**
 * 所有api请求信息
 */

export const apiAllRecord = () => fetch('/statis/api/all');

/**
 * 用户注册量
 */

export const userCount = date => fetch('/statis/user/' + date + '/count');

/**
 * 某一天订单数量
 */

export const orderCount = date => fetch('/statis/order/' + date + '/count');


/**
 * 某一天管理员注册量
 */

export const adminDayCount = date => fetch('/statis/admin/' + date + '/count');


/**
 * 管理员数量
 */

export const adminCount = () => fetch('/admin/count');

/**
 * 获取定位城市
 */

export const cityGuess = () => fetch('/v1/cities', {
	type: 'guess'
});

/**
 * 获取搜索地址
 */

export const searchplace = (cityid, value) => fetch('/v1/pois', {
	type: 'search',
	city_id: cityid,
	keyword: value
});

/**
 * 获取当前店铺食品种类
 */

export const getCategory = restaurant_id => fetch('/shopping/getcategory/' + restaurant_id);

/**
 * 添加食品种类
 */

export const addCategory = data => fetch('/shopping/addcategory', data, 'POST');


/**
 * 添加食品
 */

export const addFood = data => fetch('/shopping/addfood', data, 'POST');


/**
 * category 种类列表
 */

export const foodCategory = (latitude, longitude) => fetch('/shopping/v2/restaurant/category');

/**
 * 获取餐馆列表
 */

export const getResturants = data => fetch('/shopping/restaurants', data);

/**
 * 获取餐馆详细信息
 */

export const getResturantDetail = restaurant_id => fetch('/shopping/restaurant/' + restaurant_id);

/**
 * 获取餐馆数量
 */

export const getResturantsCount = () => fetch('/shopping/restaurants/count');

/**
 * 更新餐馆信息
 */

export const updateResturant = data => fetch('/shopping/updateshop', data, 'POST');


/**
 * 获取食品列表
 */

export const getFoods = data => fetch('/shopping/v2/foods', data);

/**
 * 获取食品数量
 */

export const getFoodsCount = data => fetch('/shopping/v2/foods/count', data);


/**
 * 获取menu列表
 */

export const getMenu = data => fetch('/shopping/v2/menu', data);

/**
 * 获取menu详情
 */

export const getMenuById = category_id => fetch('/shopping/v2/menu/' + category_id);

/**
 * 更新食品信息
 */

export const updateFood = data => fetch('/shopping/v2/updatefood', data, 'POST');

/**
 * 删除食品
 */

export const deleteFood = food_id => fetch('/shopping/v2/food/' + food_id, {}, 'DELETE');

/**
 * 获取用户列表
 */

export const getUserList = data => fetch('/v1/users/list', data);

/**
 * 获取用户数量
 */

export const getUserCount = data => fetch('/v1/users/count', data);

/**
 * 获取订单列表
 */

export const getOrderList = data => fetch('/bos/orders', data);

/**
 * 获取订单数量
 */

export const getOrderCount = data => fetch('/bos/orders/count', data);

/**
 * 获取用户信息
 */

export const getUserInfo = user_id => fetch('/v1/user/' + user_id);

/**
 * 获取地址信息
 */

export const getAddressById = address_id => fetch('/v1/addresse/' + address_id);

/**
 * 获取用户分布信息
 */

export const getUserCity = () => fetch('/v1/user/city/count');
