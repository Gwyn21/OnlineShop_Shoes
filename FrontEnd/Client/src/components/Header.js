import React, { useEffect, useRef, useState } from 'react';
import { Box, Flex, Button, Avatar, Menu, MenuButton, MenuList, MenuItem, IconButton, Link as ChakraLink, Badge, Input, InputGroup, InputRightElement, Text, useToast, Modal, ModalOverlay, ModalContent, ModalHeader, ModalBody, ModalCloseButton, Image, VStack, Progress, Center } from '@chakra-ui/react';
import { Link, useNavigate } from 'react-router-dom';
import { HamburgerIcon, SearchIcon, SunIcon } from '@chakra-ui/icons';
import { FiShoppingCart, FiCamera } from 'react-icons/fi';
import { useCart } from '../context/CartContext';

const Header = () => {
    const navigate = useNavigate();
    const isAuthenticated = !!localStorage.getItem("token");
    const [user, setUser] = React.useState(JSON.parse(localStorage.getItem("user")) || {});
    const { cart } = useCart();
    const [searchQuery, setSearchQuery] = React.useState('');
    const fileInputRef = useRef(null);
    const toast = useToast();
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [uploadedImage, setUploadedImage] = useState(null);
    const [isLoading, setIsLoading] = useState(false);
    const [detectionResult, setDetectionResult] = useState(null);

    const handleSearch = (e) => {
        e.preventDefault();
        if (searchQuery.trim()) {
            navigate(`/search?q=${encodeURIComponent(searchQuery)}`);
        }
    };

    const handleCameraClick = () => {
        setIsModalOpen(true);
    };

    const handleFileUpload = async (event) => {
        const file = event.target.files[0];
        if (!file) return;

        // Validate file type
        if (!file.type.startsWith('image/')) {
            toast({
                title: "Lỗi",
                description: "Vui lòng chọn file ảnh",
                status: "error",
                duration: 3000,
                isClosable: true,
            });
            return;
        }

        // Preview image
        setUploadedImage(URL.createObjectURL(file));
        setIsLoading(true);
        setDetectionResult(null);

        const formData = new FormData();
        formData.append('image', file);

        try {
            const response = await fetch('http://localhost:8080/api/books/detect-book', {
                method: 'POST',
                body: formData,
            });

            if (!response.ok) {
                throw new Error('Lỗi khi nhận diện');
            }

            const result = await response.text();
            setDetectionResult(result);
        } catch (error) {
            toast({
                title: "Lỗi",
                description: error.message,
                status: "error",
                duration: 3000,
                isClosable: true,
            });
            setDetectionResult("Không thể nhận diện. Vui lòng thử lại.");
        } finally {
            setIsLoading(false);
        }
    };

    const resetUpload = () => {
        setUploadedImage(null);
        setDetectionResult(null);
        setIsLoading(false);
        if (fileInputRef.current) {
            fileInputRef.current.value = '';
        }
    };

    const closeModal = () => {
        setIsModalOpen(false);
        resetUpload();
    };

    useEffect(() => {
        const handleUserChange = () => {
            const newUser = JSON.parse(localStorage.getItem("user")) || {};
            if (JSON.stringify(newUser) !== JSON.stringify(user)) {
                setUser(newUser);
                window.location.reload(); // Refresh the page
            }
        };

        // You can set an interval or use an event listener to check for changes
        const interval = setInterval(handleUserChange, 1000); // Check every second

        return () => clearInterval(interval); // Cleanup on unmount
    }, [user]);

    return (
        <>
            <Box bg="blue.500" color="white" px={4}>
                <Flex h={16} alignItems={'center'} justifyContent={'space-between'}>
                    <Flex alignItems={'center'}>
                        <Link to="/" style={{ textDecoration: 'none' }}>
                            <Flex alignItems="center" cursor="pointer">
                                <Box 
                                    bg="white" 
                                    p={2} 
                                    borderRadius="md" 
                                    mr={2}
                                    display="flex"
                                    alignItems="center"
                                >
                                    <SunIcon color="orange.400" boxSize={6} />
                                    <Text
                                        color="blue.500"
                                        fontSize="xl"
                                        fontWeight="bold"
                                        ml={1}
                                        fontFamily="'Poppins', sans-serif"
                                    >
                                        KickzHub
                                    </Text>
                                </Box>
                            </Flex>
                        </Link>
                        <form onSubmit={handleSearch} style={{ marginLeft: '16px' }}>
                            <InputGroup size="md" width="300px">
                                <Input
                                    placeholder="Tìm kiếm sản phẩm..."
                                    value={searchQuery}
                                    onChange={(e) => setSearchQuery(e.target.value)}
                                    bg="white"
                                    color="black"
                                />
                                <InputRightElement>
                                    <IconButton
                                        aria-label="Search"
                                        icon={<SearchIcon />}
                                        size="sm"
                                        type="submit"
                                        colorScheme="blue"
                                    />
                                </InputRightElement>
                            </InputGroup>
                        </form>
                        <input
                            type="file"
                            ref={fileInputRef}
                            style={{ display: 'none' }}
                            accept="image/*"
                            onChange={handleFileUpload}
                        />
                       
                        <Flex display={{ base: 'none', md: 'flex' }} ml={4}>
                            <Link to="/">
                                <Button variant="link" color="white" ml={2}>Trang chủ</Button>
                            </Link>
                            <Link to="/products">
                                <Button variant="link" color="white" ml={4}>Sản phẩm</Button>
                            </Link>
                            <Link to="/contact">
                                <Button variant="link" color="white" ml={4}>Liên hệ</Button>
                            </Link>
                            <Link to="/about">
                                <Button variant="link" color="white" ml={4}>Giới thiệu</Button>
                            </Link>
                        </Flex>
                    </Flex>
                    <Flex alignItems={'center'}>
                        <ChakraLink as={Link} to="/cart" color="white" mr={4} position="relative">
                            <FiShoppingCart size="24px" />
                            {cart.length > 0 && (
                                <Badge
                                    colorScheme="red"
                                    borderRadius="full"
                                    position="absolute"
                                    top="-1"
                                    right="-1"
                                    fontSize="0.8em"
                                >
                                    {cart.length}
                                </Badge>
                            )}
                        </ChakraLink>
                        {isAuthenticated ? (
                            <Menu>
                                <MenuButton as={Button} variant="link" color="white" display="flex" alignItems="center" h="100%">
                                    <Avatar size="sm" name={user.name} src={user.imageUrl} mr={2} />
                                    {user.username}
                                </MenuButton>
                                <MenuList>
                                    <MenuItem color="black" onClick={() => navigate('/profile')}>Hồ sơ</MenuItem>
                                    <MenuItem color="black" onClick={() => navigate('/purchased-books')}>Sản phẩm đã mua</MenuItem>
                                    <MenuItem color="black" onClick={() => navigate('/change-password')}>Đổi mật khẩu</MenuItem>
                                    <MenuItem color="black" onClick={() => {
                                        localStorage.removeItem("token");
                                        localStorage.removeItem("user");
                                        navigate('/');
                                        window.location.reload();
                                    }}>Đăng xuất</MenuItem>
                                </MenuList>
                            </Menu>
                        ) : (
                            <Link to="/login">
                                <Button variant="link" color="white">Đăng nhập</Button>
                            </Link>
                        )}
                        <IconButton
                            aria-label="Open Menu"
                            icon={<HamburgerIcon />}
                            display={{ md: 'none' }}
                            onClick={() => {
                                // Logic to open a mobile menu can be added here
                            }}
                            ml={2}
                        />
                    </Flex>
                </Flex>
            </Box>

            <Modal isOpen={isModalOpen} onClose={closeModal} size="xl">
                <ModalOverlay 
                    bg="blackAlpha.300"
                    backdropFilter="blur(10px)"
                />
            </Modal>
        </>
    );
};

export default Header;