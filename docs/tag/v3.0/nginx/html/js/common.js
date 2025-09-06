// 公共配置和工具函数
const AppConfig = {
    // 基础地址配置
    sPayMallUrl: "http://117.72.178.217",
    groupBuyMarketUrl: "http://117.72.178.217",
    goodsId: "9890001"
};

// 工具函数
const AppUtils = {
    // 获取Cookie值
    getCookie: function (name) {
        const value = `; ${document.cookie}`;
        const parts = value.split(`; ${name}=`);
        if (parts.length === 2) return parts.pop().split(';').shift();
        return null;
    },

    // 获取当前登录用户ID
    getCurrentUserId: function () {
        const userId = this.getCookie("loginToken");
        if (!userId) {
            window.location.href = "login.html"; // 跳转到登录页
            return null;
        }
        return userId;
    },

    // 混淆用户ID显示
    obfuscateUserId: function (userId) {
        if (userId.length <= 4) {
            // 如果 userId 的长度小于或等于 4，则无需替换任何字符
            return userId;
        } else {
            // 获取前两位和后两位
            const start = userId.slice(0, 2);
            const end = userId.slice(-2);
            // 计算中间部分应该被替换成多少个 *
            const middle = '*'.repeat(userId.length - 4);
            // 返回成功替换后的字符串
            return `${start}${middle}${end}`;
        }
    },

    // 从URL参数获取用户ID（可选功能）
    getUserIdFromUrl: function () {
        const urlParams = new URLSearchParams(window.location.search);
        return urlParams.get('userId') || this.getCurrentUserId();
    },

    // 显示Toast提示消息
    showToast: function(message, type = 'error') {
        // 移除已存在的提示
        const existingToast = document.querySelector('.app-toast');
        if (existingToast) {
            existingToast.remove();
        }

        // 创建新的提示元素
        const toast = document.createElement('div');
        toast.className = `app-toast toast-${type}`;
        toast.textContent = message;
        
        // 根据类型设置不同的样式
        const colors = {
            error: { bg: '#ff4757', color: '#ffffff' },
            success: { bg: '#2ed573', color: '#ffffff' },
            warning: { bg: '#ffa502', color: '#ffffff' },
            info: { bg: '#5352ed', color: '#ffffff' }
        };
        
        const typeColor = colors[type] || colors.error;
        
        toast.style.cssText = `
            position: fixed;
            top: 20px;
            left: 50%;
            transform: translateX(-50%);
            background: ${typeColor.bg};
            color: ${typeColor.color};
            padding: 12px 20px;
            border-radius: 6px;
            z-index: 9999;
            font-size: 14px;
            opacity: 0;
            transition: all 0.3s ease;
            box-shadow: 0 4px 12px rgba(0,0,0,0.3);
            max-width: 90%;
            text-align: center;
            font-weight: 500;
        `;

        document.body.appendChild(toast);

        // 显示动画
        setTimeout(() => {
            toast.style.opacity = '1';
            toast.style.transform = 'translateX(-50%) translateY(10px)';
        }, 100);

        // 3秒后移除
        setTimeout(() => {
            toast.style.opacity = '0';
            toast.style.transform = 'translateX(-50%) translateY(-10px)';
            setTimeout(() => {
                if (toast.parentNode) {
                    toast.parentNode.removeChild(toast);
                }
            }, 300);
        }, 3000);
    },

    // API错误处理封装
    handleApiResponse: function(response, successCallback, errorCallback) {
        if (response.code === '0000') {
            if (successCallback) {
                successCallback(response.data);
            }
        } else {
            const errorMessage = response.info || response.message || '操作失败，请重试';
            this.showToast(errorMessage, 'error');
            if (errorCallback) {
                errorCallback(response);
            }
        }
    }
};

// 导出配置和工具函数到全局
window.AppConfig = AppConfig;
window.AppUtils = AppUtils;
window.getCookie = AppUtils.getCookie;
window.obfuscateUserId = AppUtils.obfuscateUserId;