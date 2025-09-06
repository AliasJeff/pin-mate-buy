// 测试回调页面的JavaScript逻辑

// 页面加载完成后初始化
document.addEventListener('DOMContentLoaded', function() {
    initTestCallback();
});

function initTestCallback() {
    const testBtn = document.getElementById('testBtn');
    const outTradeNoInput = document.getElementById('outTradeNo');
    const resultSection = document.getElementById('resultSection');
    const resultContent = document.getElementById('resultContent');
    
    // 绑定测试按钮点击事件
    testBtn.addEventListener('click', function() {
        const outTradeNo = outTradeNoInput.value.trim();
        
        if (!outTradeNo) {
            showError('请输入商户订单号');
            return;
        }
        
        // 验证订单号格式（简单验证）
        if (outTradeNo.length < 6) {
            showError('商户订单号格式不正确');
            return;
        }
        
        executeTestCallback(outTradeNo);
    });
    
    // 回车键提交
    outTradeNoInput.addEventListener('keypress', function(e) {
        if (e.key === 'Enter') {
            testBtn.click();
        }
    });
}

// 执行测试回调
function executeTestCallback(outTradeNo) {
    const testBtn = document.getElementById('testBtn');
    const btnText = testBtn.querySelector('.btn-text');
    const loading = testBtn.querySelector('.loading');
    const resultSection = document.getElementById('resultSection');
    const resultContent = document.getElementById('resultContent');
    
    // 设置加载状态
    testBtn.disabled = true;
    btnText.style.display = 'none';
    loading.style.display = 'inline-block';
    
    // 隐藏之前的结果
    resultSection.style.display = 'none';
    resultSection.className = 'result-section';
    
    // 构建请求URL
    const apiUrl = `${getApiBaseUrl()}/api/v1/alipay/active_pay_notify`;
    
    // 发送POST请求
    fetch(apiUrl, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: `outTradeNo=${encodeURIComponent(outTradeNo)}`
    })
    .then(response => {
        if (!response.ok) {
            throw new Error(`HTTP ${response.status}: ${response.statusText}`);
        }
        return response.json();
    })
    .then(data => {
        console.log('API响应:', data);
        if (data.code === '0000') {
            showResult(data, true);
        } else {
            const errorMessage = data.info || data.message || '回调测试失败';
            AppUtils.showToast(errorMessage, 'error');
            showResult(data, false);
        }
    })
    .catch(error => {
        console.error('请求失败:', error);
        AppUtils.showToast('网络错误，请稍后重试', 'error');
        showResult({
            code: 'ERROR',
            info: '请求失败',
            data: error.message
        }, false);
    })
    .finally(() => {
        // 恢复按钮状态
        testBtn.disabled = false;
        btnText.style.display = 'inline-block';
        loading.style.display = 'none';
    });
}

// 显示结果
function showResult(data, isSuccess) {
    const resultSection = document.getElementById('resultSection');
    const resultContent = document.getElementById('resultContent');
    
    // 格式化显示结果
    let resultText = '';
    if (isSuccess && data.code === '0000') {
        resultText = `✅ 测试成功\n\n`;
        resultText += `响应码: ${data.code}\n`;
        resultText += `响应信息: ${data.info}\n`;
        resultText += `处理结果: ${data.data}\n`;
        resultText += `\n🎉 订单状态已成功更新！`;
        
        resultSection.className = 'result-section result-success';
    } else {
        resultText = `❌ 测试失败\n\n`;
        resultText += `响应码: ${data.code || 'ERROR'}\n`;
        resultText += `响应信息: ${data.info || '未知错误'}\n`;
        resultText += `错误详情: ${data.data || '请检查订单号是否正确'}\n`;
        
        resultSection.className = 'result-section result-error';
    }
    
    resultContent.textContent = resultText;
    resultSection.style.display = 'block';
    
    // 滚动到结果区域
    resultSection.scrollIntoView({ behavior: 'smooth', block: 'nearest' });
}

// 显示错误提示
function showError(message) {
    // 使用统一的 toast 提示
    AppUtils.showToast(message, 'warning');
}

// 获取API基础URL
function getApiBaseUrl() {
    // 从AppConfig中获取API地址
    if (window.AppConfig && window.AppConfig.sPayMallUrl) {
        return window.AppConfig.sPayMallUrl;
    }
    
    // 如果AppConfig不可用，则使用默认配置
    const hostname = window.location.hostname;
    if (hostname === 'localhost' || hostname === '127.0.0.1') {
        return 'http://localhost:8080';
    } else {
        // 生产环境或其他环境的API地址
        return `http://${hostname}:8080`;
    }
}

// 工具函数：复制结果到剪贴板
function copyResult() {
    const resultContent = document.getElementById('resultContent');
    const text = resultContent.textContent;
    
    if (navigator.clipboard) {
        navigator.clipboard.writeText(text).then(() => {
            AppUtils.showToast('结果已复制到剪贴板', 'success');
        }).catch(err => {
            console.error('复制失败:', err);
            AppUtils.showToast('复制失败', 'error');
        });
    } else {
        // 兼容旧浏览器
        const textArea = document.createElement('textarea');
        textArea.value = text;
        document.body.appendChild(textArea);
        textArea.select();
        try {
            const successful = document.execCommand('copy');
            if (successful) {
                AppUtils.showToast('结果已复制到剪贴板', 'success');
            } else {
                AppUtils.showToast('复制失败', 'error');
            }
        } catch (err) {
            console.error('复制失败:', err);
            AppUtils.showToast('复制失败', 'error');
        }
        document.body.removeChild(textArea);
    }
}

// 注意：showToast方法现在使用统一的AppUtils.showToast