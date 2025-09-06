// 订单明细页面JavaScript
class OrderListManager {
    constructor() {
        this.userId = AppUtils.getUserIdFromUrl(); // 从公共工具获取用户ID
        this.lastId = null;
        this.pageSize = 20;
        this.hasMore = true;
        this.loading = false;
        this.currentRefundOrderId = null;

        this.init();
    }

    init() {
        this.bindEvents();
        this.displayUserId();
        this.loadOrderList();
    }

    bindEvents() {
        // 加载更多按钮事件
        // document.getElementById('loadMoreBtn').addEventListener('click', () => {
        //     this.loadOrderList();
        // });

        // 退单弹窗事件
        document.getElementById('cancelRefund').addEventListener('click', () => {
            this.hideRefundModal();
        });

        document.getElementById('confirmRefund').addEventListener('click', () => {
            this.processRefund();
        });

        // 点击弹窗外部关闭
        document.getElementById('refundModal').addEventListener('click', (e) => {
            if (e.target.id === 'refundModal') {
                this.hideRefundModal();
            }
        });
    }

    displayUserId() {
        const userIdElement = document.getElementById('userIdDisplay');
        if (userIdElement && this.userId) {
            userIdElement.textContent = `用户ID: ${AppUtils.obfuscateUserId(this.userId)}`;
        }
    }

    async loadOrderList() {
        if (this.loading || !this.hasMore) return;

        this.loading = true;
        this.showLoading();

        try {
            const requestData = {
                userId: this.userId,
                lastId: this.lastId,
                pageSize: this.pageSize
            };

            // 调用后端API
            const response = await fetch(AppConfig.sPayMallUrl + '/api/v1/alipay/query_user_order_list', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(requestData)
            });

            const result = await response.json();

            if (result.code === '0000' && result.data) {
                // 按 id 倒序排序
                const sortedOrderList = result.data.orderList.sort((a, b) => b.id - a.id);

                this.renderOrderList(sortedOrderList, this.lastId === null);
                this.hasMore = result.data.hasMore;
                this.lastId = result.data.lastId;

                // 更新加载更多按钮状态
                // this.updateLoadMoreButton();
            } else {
                const errorMessage = result.info || result.message || '加载订单列表失败';
                AppUtils.showToast(errorMessage, 'error');
            }
        } catch (error) {
            console.error('加载订单列表出错:', error);
            AppUtils.showToast('网络错误，请稍后重试', 'error');
        } finally {
            this.loading = false;
            this.hideLoading();
        }
    }

    renderOrderList(orders, isFirstLoad = false) {
        const orderListElement = document.getElementById('orderList');
        const emptyStateElement = document.getElementById('emptyState');

        if (isFirstLoad) {
            orderListElement.innerHTML = '';
        }

        if (orders && orders.length > 0) {
            emptyStateElement.style.display = 'none';

            orders.forEach(order => {
                const orderElement = this.createOrderElement(order);
                orderListElement.appendChild(orderElement);
            });

            // 启动倒计时功能（参考index.html实现）
            document.querySelectorAll('.countdown').forEach(el => {
                if (typeof Countdown !== 'undefined') {
                    new Countdown(el, el.textContent);
                } else {
                    // 如果Countdown类不存在，使用简单的倒计时显示
                    console.warn('Countdown class not found, displaying static countdown');
                }
            });
        } else if (isFirstLoad) {
            emptyStateElement.style.display = 'block';
        }
    }

    createOrderElement(order) {
        const orderDiv = document.createElement('div');
        orderDiv.className = 'order-item';
        
        // 构建倒计时HTML
        const countdownHtml = order.validTimeCountdown && order.status === 'PAY_WAIT' 
            ? `<div class="order-countdown"><span class="countdown">${order.validTimeCountdown}</span></div>` 
            : '';
        
        // 构建支付按钮HTML
        const payButtonHtml = order.status === 'PAY_WAIT' 
            ? `<button class="pay-btn" onclick="orderManager.processPay('${order.orderId}', '${order.payAmount || order.totalAmount}')">去支付</button>` 
            : '';
        
        orderDiv.innerHTML = `
            <div class="order-header">
                <div class="order-id" onclick="orderManager.copyOrderId('${order.orderId}')" title="点击复制订单号">
                    订单号: <span class="order-id-text">${order.orderId}</span>
                    <span class="copy-icon">📋</span>
                </div>
                <div class="order-status status-${order.status}">${this.getStatusText(order.status)}</div>
            </div>
            <div class="order-content">
                <div class="product-name">${order.productName || '商品名称'}</div>
                <div class="order-details">
                    <div class="order-time">${this.formatTime(order.orderTime)}</div>
                    <div class="pay-amount">¥${order.payAmount || order.totalAmount}</div>
                </div>
                ${countdownHtml}
            </div>
            <div class="order-actions">
                ${payButtonHtml}
                <button class="refund-btn" 
                        onclick="orderManager.showRefundModal('${order.orderId}')"
                        ${order.status === 'CLOSE' ? 'disabled' : ''}>
                    ${order.status === 'CLOSE' ? '已关闭' : '申请退单'}
                </button>
            </div>
        `;

        return orderDiv;
    }

    getStatusText(status) {
        const statusMap = {
            'CREATE': '新创建',
            'PAY_WAIT': '等待支付',
            'PAY_SUCCESS': '支付成功',
            'DEAL_DONE': '交易完成',
            'CLOSE': '已关闭',
            'WAIT_REFUND': '退款中',
        };
        return statusMap[status] || status;
    }

    formatTime(timeStr) {
        if (!timeStr) return '';
        const date = new Date(timeStr);
        return `${date.getFullYear()}-${String(date.getMonth() + 1).padStart(2, '0')}-${String(date.getDate()).padStart(2, '0')} ${String(date.getHours()).padStart(2, '0')}:${String(date.getMinutes()).padStart(2, '0')}`;
    }

    // updateLoadMoreButton() {
    //     const loadMoreBtn = document.getElementById('loadMoreBtn');
    //     if (this.hasMore) {
    //         loadMoreBtn.style.display = 'block';
    //         loadMoreBtn.disabled = false;
    //         loadMoreBtn.textContent = '加载更多';
    //     } else {
    //         loadMoreBtn.style.display = 'none';
    //     }
    // }

    showRefundModal(orderId) {
        this.currentRefundOrderId = orderId;
        document.getElementById('refundModal').style.display = 'flex';
    }

    hideRefundModal() {
        document.getElementById('refundModal').style.display = 'none';
        this.currentRefundOrderId = null;
    }

    async processRefund() {
        if (!this.currentRefundOrderId) return;

        this.showLoading();

        try {
            const requestData = {
                userId: this.userId,
                orderId: this.currentRefundOrderId
            };

            const response = await fetch(AppConfig.sPayMallUrl + '/api/v1/alipay/refund_order', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(requestData)
            });

            const result = await response.json();

            if (result.code === '0000' && result.data && result.data.success) {
                AppUtils.showToast('退单成功', 'success');
                this.hideRefundModal();
                // 重新加载订单列表
                this.refreshOrderList();
            } else {
                const errorMessage = result.info || result.data?.message || '退单失败';
                AppUtils.showToast(errorMessage, 'error');
            }
        } catch (error) {
            console.error('退单操作出错:', error);
            AppUtils.showToast('网络错误，请稍后重试', 'error');
        } finally {
            this.hideLoading();
        }
    }

    refreshOrderList() {
        this.lastId = null;
        this.hasMore = true;
        document.getElementById('orderList').innerHTML = '';
        this.loadOrderList();
    }

    showLoading() {
        document.getElementById('loadingTip').style.display = 'block';
    }

    hideLoading() {
        document.getElementById('loadingTip').style.display = 'none';
    }

    showError(message) {
        // 使用统一的 toast 提示
        AppUtils.showToast(message, 'error');
    }

    showSuccess(message) {
        // 使用统一的 toast 提示
        AppUtils.showToast(message, 'success');
    }

    // 复制订单号功能
    copyOrderId(orderId) {
            if (navigator.clipboard) {
                navigator.clipboard.writeText(orderId).then(() => {
                    AppUtils.showToast('订单号已复制到剪贴板', 'success');
                }).catch(err => {
                    console.error('复制失败:', err);
                    this.fallbackCopyTextToClipboard(orderId);
                });
            } else {
                this.fallbackCopyTextToClipboard(orderId);
            }
    }

    // 兼容旧浏览器的复制方法
    fallbackCopyTextToClipboard(text) {
        const textArea = document.createElement('textarea');
        textArea.value = text;
        textArea.style.position = 'fixed';
        textArea.style.left = '-999999px';
        textArea.style.top = '-999999px';
        document.body.appendChild(textArea);
        textArea.focus();
        textArea.select();

        try {
            const successful = document.execCommand('copy');
            if (successful) {
                AppUtils.showToast('订单号已复制到剪贴板', 'success');
            } else {
                AppUtils.showToast('复制失败，请手动复制', 'warning');
            }
        } catch (err) {
            console.error('复制失败:', err);
            AppUtils.showToast('复制失败，请手动复制', 'warning');
        }

        document.body.removeChild(textArea);
    }

    // 处理支付
    async processPay(orderId, amount) {
        this.showPaymentConfirm(amount, orderId);
    }

    // 支付确认弹窗（参考index.html实现）
    showPaymentConfirm(price, orderId) {
        // 创建遮罩层
        const overlay = document.createElement('div');
        overlay.className = 'payment-overlay';
        overlay.style.cssText = `
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background: rgba(0, 0, 0, 0.5);
            display: flex;
            justify-content: center;
            align-items: center;
            z-index: 1000;
        `;

        // 创建弹窗内容
        const modal = document.createElement('div');
        modal.className = 'payment-modal';
        modal.style.cssText = `
            background: white;
            padding: 20px;
            border-radius: 8px;
            max-width: 400px;
            width: 90%;
            text-align: center;
        `;
        modal.innerHTML = `
        <h3>支付确认</h3>
        <p>订单号：${orderId}</p>
        <p>商品金额：￥${price}</p>
        <p>买家账号：<span class="buyer-account">
            <span class="account-text">bcywss3672@sandbox.com</span>
            <button class="copy-btn" data-copy="bcywss3672@sandbox.com" title="复制账号" style="margin-left: 8px; border: none; background: none; cursor: pointer;">
                <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                    <rect x="9" y="9" width="13" height="13" rx="2" ry="2"></rect>
                    <path d="m5 15H4a2 2 0 0 1-2-2V4a2 2 0 0 1 2-2h9a2 2 0 0 1 2 2v1"></path>
                </svg>
            </button>
        </span></p>
        <p>登录密码：111111</p>
        <p>支付密码：111111</p>
        <p>点击"确认支付"后将跳转至支付页面，支付完成后会自动返回平台。</p>
        <div class="modal-buttons" style="margin-top: 20px;">
            <button class="confirm-btn" style="background: #007bff; color: white; border: none; padding: 10px 20px; margin: 0 10px; border-radius: 4px; cursor: pointer;">确认支付</button>
            <button class="cancel-btn" style="background: #6c757d; color: white; border: none; padding: 10px 20px; margin: 0 10px; border-radius: 4px; cursor: pointer;">取消支付</button>
        </div>
    `;

        // 确认支付处理
        modal.querySelector('.confirm-btn').addEventListener('click', () => {
            this.processOrderPayment(orderId);
            overlay.remove();
        });

        // 取消支付处理
        modal.querySelector('.cancel-btn').addEventListener('click', () => {
            document.querySelectorAll('form').forEach(form => form.remove());
            overlay.remove();
        });

        // 添加复制功能
        modal.querySelector('.copy-btn').addEventListener('click', function () {
            const textToCopy = this.getAttribute('data-copy');
            const button = this;

            if (navigator.clipboard && navigator.clipboard.writeText) {
                navigator.clipboard.writeText(textToCopy).then(() => {
                    orderManager.showCopySuccess(button);
                }).catch(err => {
                    console.error('无法复制文本: ', err);
                    orderManager.fallbackCopy(textToCopy, button);
                });
            } else {
                orderManager.fallbackCopy(textToCopy, button);
            }
        });

        overlay.appendChild(modal);
        document.body.appendChild(overlay);
    }

    // 处理订单支付（调用支付API）
    async processOrderPayment(orderId) {
        this.showLoading();

        try {
            const requestData = {
                userId: this.userId,
                orderId: orderId
            };

            const response = await fetch(AppConfig.sPayMallUrl + '/api/v1/alipay/get_pay_order', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(requestData)
            });

            const result = await response.json();

            if (result.code === '0000' && result.data) {
                document.querySelectorAll('form').forEach(form => form.remove());
                document.body.insertAdjacentHTML('beforeend', result.data);
                const form = document.querySelector('form');
                if (form) form.submit();
            } else {
                const errorMessage = result.info || result.message || '获取支付信息失败';
                AppUtils.showToast(errorMessage, 'error');
            }
        } catch (error) {
            console.error('处理支付出错:', error);
            AppUtils.showToast('网络错误，请稍后重试', 'error');
        } finally {
            this.hideLoading();
        }
    }

    // 复制成功提示（参考index.html）
    showCopySuccess(button) {
        const originalHtml = button.innerHTML;
        button.innerHTML = `✔️ 已复制`;
        button.style.color = '#48bb78';
        setTimeout(() => {
            button.innerHTML = originalHtml;
            button.style.color = '';
        }, 1500);
    }

    // 兼容复制方法（参考index.html）
    fallbackCopy(text, button) {
        const textarea = document.createElement('textarea');
        textarea.value = text;
        textarea.style.position = 'fixed';
        textarea.style.left = '-999999px';
        textarea.style.top = '-999999px';
        document.body.appendChild(textarea);
        textarea.focus();
        textarea.select();
        try {
            const successful = document.execCommand('copy');
            if (successful) {
                this.showCopySuccess(button);
            } else {
                alert('复制失败，请手动选择文本');
            }
        } catch (err) {
            console.error('execCommand 复制失败', err);
            alert('复制失败，请手动选择文本');
        }
        document.body.removeChild(textarea);
    }

    // 注意：showToast方法现在使用统一的AppUtils.showToast
}

// 页面加载完成后初始化
let orderManager;
document.addEventListener('DOMContentLoaded', function () {
    orderManager = new OrderListManager();
});