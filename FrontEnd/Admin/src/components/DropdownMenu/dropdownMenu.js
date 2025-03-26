import { LogoutOutlined, SettingOutlined, UserOutlined } from '@ant-design/icons';
import { Avatar, Dropdown, Menu, Row } from 'antd';
import React, { useEffect, useState } from 'react';
import { useHistory } from "react-router-dom";
import { getUserFromLocalStorage } from '../../services/shareService';
import "./dropdownMenu.css";

function DropdownAvatar() {

  const [userData, setUserData] = useState([]);
  let history = useHistory();

  const Logout = async () => {
    localStorage.clear();
    window.location.reload();
  }

  useEffect(() => {
    (async () => {
      try {
        setUserData(getUserFromLocalStorage());
      } catch (error) {
        console.log('Failed to fetch profile user:' + error);
      }
    })();
  }, [])

  const handleRouter = (link) => {
    history.push(link);
  }

  const avatar = (
    <Menu>
      <Menu.Item icon={<SettingOutlined />} >
      <a target="_blank" rel="noopener noreferrer" onClick={() => handleRouter("/change-password/"+ userData.id)}>
         Thay đổi mật khẩu
        </a>
      </Menu.Item>
      <Menu.Item key="3" icon={<LogoutOutlined />} onClick={Logout}  >
        <a target="_blank" rel="noopener noreferrer" >
          Thoát
        </a>
      </Menu.Item>
    </Menu>
  );


  return (
    <Dropdown key="avatar" placement="bottomCenter" overlay={avatar} arrow>
      <Row
        style={{
          paddingLeft: 5, paddingRight: 5, cursor: 'pointer'
        }}
        className="container"
      >
        <div style={{ display: 'flex', alignItems: "center", justifyContent: "center" }}>
          <div style={{ paddingRight: 10 }}>
            <Avatar
              style={{
                outline: 'none',
              }}
              src={userData?.imageUrl}
            />
          </div>
          <p style={{ padding: 0, margin: 0, textTransform: 'capitalize', color: "#000000" }} >
            {userData?.username}
          </p>
        </div>
      </Row>
    </Dropdown>
  );
};

export default DropdownAvatar;