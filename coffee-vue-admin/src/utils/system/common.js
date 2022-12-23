export function getTreeData(data){
    return data.filter(parent=>{
        let branchArr = data.filter(child => parent['id'] === child['parentId']);
        branchArr.length>0 ? parent['children'] = branchArr : '';
        return parent['parentId'] === 1 ;
    })
}
// 手写深拷贝
export function deepClone(obj) {
    //判断 传入对象 为 数组 或者 对象
    var result = Array.isArray(obj) ? [] : {};
    // for in 遍历
    for (var key in obj) {
        // 判断 是否 为自身 的属性值（排除原型链干扰）
        if (obj.hasOwnProperty(key)) {
            // 判断 对象的属性值 中 存储的 数据类型 是否为对象
            if (typeof obj[key] === 'object') {
                // 有可能等于 null
                if (obj[key] === null ) {
                    result[key] = null
                    continue
                }
                // 递归调用
                result[key] = deepClone(obj[key]);   //递归复制
            }
            // 不是的话 直接 赋值 copy
            else {
                result[key] = obj[key];
            }
        }
    }
    // 返回 新的对象
    return result;
}
