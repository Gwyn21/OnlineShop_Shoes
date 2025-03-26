import { DashboardOutlined, FormOutlined, ShoppingCartOutlined, ShoppingOutlined, UserOutlined } from '@ant-design/icons';
import { Layout, Menu } from 'antd';
import React, { useEffect, useState } from 'react';
import { useHistory, useLocation } from "react-router-dom";
import "./sidebar.css";

const { Sider } = Layout;

function Sidebar() {
  const history = useHistory();
  const location = useLocation();
  const [userRole, setUserRole] = useState('');

  useEffect(() => {
    const user = JSON.parse(localStorage.getItem('user'));
    if (user) {
      setUserRole(user.role);
    }
  }, []);

  const menuSidebarAdmin = [
    {
      key: "dash-board",
      title: "Dashboards",
      link: "/dash-board",
      icon: <DashboardOutlined />,
      roles: ["ADMIN", "STAFF"]
    },
    {
      key: "account-management",
      title: "Quản lý tài khoản",
      link: "/account-management",
      icon: <UserOutlined />,
      roles: ["ADMIN"]
    },
    {
      key: "product-list",
      title: "Danh sách sản phẩm",
      link: "/product-list",
      icon: <FormOutlined />,
      roles: ["ADMIN", "STAFF"]
    },
    {
      key: "category-list",
      title: "Danh mục sản phẩm",
      link: "/category-list",
      icon: <ShoppingOutlined />,
      roles: ["ADMIN", "STAFF"]
    },
    {
      key: "promotions-management",
      title: "Quản lý khuyến mãi",
      link: "/promotions-management",
      icon: <ShoppingOutlined />,
      roles: ["ADMIN"]
    },
    {
      key: "order-list",
      title: "Quản lý đơn hàng",
      link: "/order-list",
      icon: <ShoppingCartOutlined />,
      roles: ["ADMIN", "STAFF"]
    },
  ];

  const navigate = (link, key) => {
    history.push(link);
  }

  const filteredMenu = menuSidebarAdmin.filter(item => 
    item.roles.includes(userRole)
  );

  return (
    <Sider
      className={'ant-layout-sider-trigger'}
      width={215}
      style={{
        position: "fixed",
        top: 55,
        height: '100%',
        left: 0,
        padding: 0,
        zIndex: 1,
        marginTop: 0,
        boxShadow: " 0 1px 4px -1px rgb(0 0 0 / 15%)"
      }}
    >
      <Menu
        mode="inline"
        selectedKeys={location.pathname.split("/")}
        defaultOpenKeys={['account']}
        style={{ height: '100%', borderRight: 0, backgroundColor: "#FFFFFF" }}
        theme='light'
      >
        {filteredMenu.map((map) => (
          <Menu.Item
            onClick={() => navigate(map.link, map.key)}
            key={map.key}
            icon={map.icon}
            className="customeClass"
          >
            {map.title}
          </Menu.Item>
        ))}
      </Menu>
    </Sider>
  );
}

export default Sidebar;