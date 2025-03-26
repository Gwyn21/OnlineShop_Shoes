import { LockOutlined, UserOutlined } from '@ant-design/icons';
import { Alert, Button, Divider, Form, Input, Modal, notification } from 'antd';
import React, { useEffect, useState } from 'react';
import { useHistory } from "react-router-dom";
import userApi from "../../apis/userApi";
import "./login.css";

const Login = () => {

  const [isLogin, setLogin] = useState(true);
  const [forgotPasswordModalVisible, setForgotPasswordModalVisible] = useState(false);
  const [forgotPasswordForm] = Form.useForm(); 

  let history = useHistory();

  const onFinish = values => {
    userApi.login(values.username, values.password)
      .then(function (response) {
        if (!response.user) {
          setLogin(false);
        }
        else {
          (async () => {
            try {
              console.log(response);
              if (response.user.role === "ADMIN" || response.user.role === "STAFF") {
                history.push("/dash-board");
              } else {
                notification["error"]({
                  message: `Thông báo`,
                  description:
                    'Bạn không có quyền truy cập vào hệ thống',
                });
              }
            } catch (error) {
              console.log('Failed to fetch ping role:' + error);
            }
          })();
        }
      })
      .catch(error => {
        console.log("username or password error" + error)
      });
  }


  const handleForgotPasswordCancel = () => {
    setForgotPasswordModalVisible(false);
  };

  const handleForgotPasswordSubmit = async () => {
    const values = await forgotPasswordForm.validateFields(); 
    console.log(values.username);

    try {
      const data = {
        "username": values.username
      }
      await userApi.forgotPassword(data);
      notification.success({
        message: 'Thông báo',
        description: 'Đã gửi đường dẫn đổi mật khẩu qua email.',
      });
      setForgotPasswordModalVisible(false);
    } catch (error) {
      notification.error({
        message: 'Lỗi',
        description: 'Đã có lỗi xảy ra khi gửi đường dẫn đổi mật khẩu.',
      });
      console.error('Forgot password error:', error);
    }
  };

  const handleLink = () => {
    history.push("/register");
  }

  useEffect(() => {

  }, [])

  return (
    <div className="imageBackground">
      <div id="formContainer">
        <div id="form-Login">
          <div className="formContentLeft">
            <img 
              className="formImg" 
              src="https://images.unsplash.com/photo-1441986300917-64674bd600d8?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1170&q=80" 
              alt='login-background'
            />
          </div>
          <Form
            name="normal_login"
            className="loginform"
            initialValues={{
              remember: true,
            }}
            onFinish={onFinish}
          >
            <Form.Item style={{ marginBottom: 24 }}>
              <h1 style={{ 
                fontSize: '28px', 
                fontWeight: 'bold',
                textAlign: 'center',
                background: 'linear-gradient(135deg, #00b09b, #96c93d)',
                WebkitBackgroundClip: 'text',
                WebkitTextFillColor: 'transparent'
              }}>
                ĐĂNG NHẬP HỆ THỐNG
              </h1>
              <p className="text" style={{ textAlign: 'center' }}>
                Vui lòng đăng nhập để tiếp tục
              </p>
            </Form.Item>

            {!isLogin && (
              <Form.Item style={{ marginBottom: 24 }}>
                <Alert
                  message="Tài khoản hoặc mật khẩu không chính xác"
                  type="error"
                  showIcon
                  style={{ borderRadius: '8px' }}
                />
              </Form.Item>
            )}

            <Form.Item
              name="username"
              rules={[
                {
                  required: true,
                  message: 'Vui lòng nhập tên người dùng!',
                },
              ]}
            >
              <Input
                size="large"
                prefix={<UserOutlined className="siteformitemicon" />}
                placeholder="Tên người dùng"
              />
            </Form.Item>

            <Form.Item
              name="password"
              rules={[
                {
                  required: true,
                  message: 'Vui lòng nhập mật khẩu!',
                },
              ]}
            >
              <Input.Password
                size="large"
                prefix={<LockOutlined className="siteformitemicon" />}
                placeholder="Mật khẩu"
              />
            </Form.Item>

            <Form.Item style={{ marginBottom: 12 }}>
              <Button 
                className="button" 
                type="primary" 
                htmlType="submit"
                size="large"
              >
                Đăng Nhập
              </Button>
            </Form.Item>

            <Form.Item style={{ textAlign: 'center', marginBottom: 0 }}>
              <Button 
                type="link" 
                onClick={() => setForgotPasswordModalVisible(true)}
                style={{ color: '#00b09b' }}
              >
                Quên mật khẩu?
              </Button>
            </Form.Item>
          </Form>
        </div>

        <Modal
          title={
            <div style={{ textAlign: 'center', color: '#00b09b' }}>
              <h3>Quên Mật Khẩu</h3>
            </div>
          }
          visible={forgotPasswordModalVisible}
          onCancel={handleForgotPasswordCancel}
          footer={[
            <Button key="back" onClick={handleForgotPasswordCancel}>
              Hủy
            </Button>,
            <Button 
              key="submit" 
              type="primary" 
              onClick={handleForgotPasswordSubmit}
              style={{ background: '#00b09b', borderColor: '#00b09b' }}
            >
              Gửi yêu cầu
            </Button>,
          ]}
          centered
        >
          <Form
            name="forgot_password"
            onFinish={handleForgotPasswordSubmit}
            form={forgotPasswordForm}
          >
            <Form.Item
              name="username"
              rules={[
                {
                  required: true,
                  message: 'Vui lòng nhập tên người dùng',
                },
              ]}
            >
              <Input 
                size="large"
                prefix={<UserOutlined style={{ color: '#00b09b' }} />} 
                placeholder="Tên người dùng" 
              />
            </Form.Item>
          </Form>
        </Modal>
      </div>
    </div>
  );
};

export default Login;


