import React from 'react';
import {
    Box, Text, VStack, Button, Divider, Select, Image, HStack, Heading, Flex, Icon, Badge, useToast
} from '@chakra-ui/react';
import { FaTruck, FaMoneyBillWave, FaCreditCard } from 'react-icons/fa';
import { useCart } from '../context/CartContext';
import shippingAddressApi from '../apis/shippingAddressApi';
import orderApi from '../apis/orderApi';
import vnpayApi from '../apis/vnpayApi';
import { useNavigate } from 'react-router-dom'; 

const Checkout = () => {
    const { cart, discountedTotal, clearCart } = useCart();
    const [shippingAddresses, setShippingAddresses] = React.useState([]);
    const [selectedAddress, setSelectedAddress] = React.useState(null);
    const [paymentMethod, setPaymentMethod] = React.useState('');
    const toast = useToast();
    const navigate = useNavigate();

    // Check for book types in the cart
    const hasAudioOrOnline = cart.some(item => item.bookType === 'AUDIO' || item.bookType === 'ONLINE');

    React.useEffect(() => {
        const fetchShippingAddresses = async () => {
            const user = JSON.parse(localStorage.getItem('user'));
            if (user) {
                const addresses = await shippingAddressApi.getShippingAddressesByUserId(user.id);
                setShippingAddresses(addresses);
            }
        };
        fetchShippingAddresses();
    }, []);

    const handlePayment = async (method) => {
        setPaymentMethod(method);
        const user = JSON.parse(localStorage.getItem('user'));
        if (!user || !selectedAddress) {
            toast({
                title: 'Lỗi',
                description: 'Vui lòng chọn địa chỉ giao hàng và đảm bảo bạn đã đăng nhập.',
                status: 'error',
                duration: 5000,
                isClosable: true,
            });
            return;
        }

        const orderData = {
            userId: user.id,
            items: cart.map(item => ({
                productId: item.id,
                quantity: item.quantity
            })),
            status: 'pending',
            description: 'Đã đặt hàng thành công!',
            shippingAddressId: selectedAddress,
            paymentMethod: method,
            totalAmount: discountedTotal
        };

        if (method === 'VNPay') {
            try {
                // Lưu orderData vào localStorage trước khi chuyển đến trang thanh toán VNPay
                localStorage.setItem('orderData', JSON.stringify(orderData));
                
                const response = await vnpayApi.createPayment({
                    price: discountedTotal,
                    return_url: `${window.location.origin}/success`,
                    ipAddr: '127.0.0.1'
                });
                window.location.href = response.paymentUrl;
            } catch (error) {
                console.error('Lỗi khi tạo thanh toán VNPay:', error);
                toast({
                    title: 'Lỗi',
                    description: 'Không thể tạo thanh toán VNPay. Vui lòng thử lại.',
                    status: 'error',
                    duration: 5000,
                    isClosable: true,
                });
            }
        } else if (method === 'COD') {
            // Handle COD payment
            try {
                const response = await orderApi.createOrder(orderData);
                console.log('Đơn hàng đã được tạo thành công:', response);
                toast({
                    title: 'Thành công',
                    description: 'Đơn hàng đã được đặt thành công!',
                    status: 'success',
                    duration: 5000,
                    isClosable: true,
                });
                clearCart();
                navigate('/success');
            } catch (error) {
                console.error('Lỗi khi tạo đơn hàng:', error);
                toast({
                    title: 'Lỗi',
                    description: 'Đặt hàng không thành công. Vui lòng thử lại.',
                    status: 'error',
                    duration: 5000,
                    isClosable: true,
                });
            }
        }
    };

    const formatCurrency = (value) => {
        return new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(value);
    };

    return (
        <Box p={8} maxW="900px" mx="auto" bg="white" boxShadow="xl" borderRadius="lg">
            <Heading as="h1" size="xl" mb={6} textAlign="center" color="teal.600">
                Thanh Toán
            </Heading>
            <Text fontSize="lg" color="gray.600" textAlign="center" mb={8}>
                Hoàn tất mua hàng của bạn bằng cách xem lại giỏ hàng và chọn địa chỉ giao hàng.
            </Text>
            <Divider mb={6} />

            {/* Chọn địa chỉ giao hàng */}
            <VStack align="start" spacing={4}>
                <Text fontWeight="bold" fontSize="lg">Địa chỉ giao hàng</Text>
                <Select
                    placeholder="Chọn địa chỉ giao hàng"
                    onChange={(e) => setSelectedAddress(e.target.value)}
                    borderColor="teal.400"
                >
                    {shippingAddresses.map(address => (
                        <option key={address.id} value={address.id}>
                            {`${address.addressLine}, ${address.city}, ${address.state}, ${address.postalCode}, ${address.country}`}
                        </option>
                    ))}
                </Select>

                {!selectedAddress && (
                    <Text color="red.500" fontSize="sm">Vui lòng chọn địa chỉ giao hàng để tiếp tục.</Text>
                )}
            </VStack>

            <Divider my={6} />

            {/* Danh sách sản phẩm trong giỏ hàng */}
            <VStack align="start" spacing={4} w="full">
                <Text fontWeight="bold" fontSize="lg">Sản phẩm trong giỏ hàng</Text>
                {cart.map(item => (
                    <HStack key={item.id} spacing={4} w="full" p={4} bg="gray.50" borderRadius="md" boxShadow="md">
                        <Image src={item.image} alt={item.name} boxSize="100px" borderRadius="md" />
                        <VStack align="start" spacing={2} w="full">
                            <Text fontWeight="bold" fontSize="lg">{item.name}</Text>
                            <Text fontSize="sm">Thương hiệu: {item.brand}</Text>
                            <Text fontSize="sm">Số lượng: <Badge colorScheme="teal">{item.quantity}</Badge></Text>
                            <Text fontSize="sm">Loại sản phẩm: <Badge colorScheme="blue">{item.productType}</Badge></Text>
                            <Text color="red.500" fontWeight="bold">{formatCurrency(item.price)}</Text>
                            <Text color="gray.600">Tổng: {formatCurrency(discountedTotal)}</Text>
                        </VStack>
                    </HStack>
                ))}
                
                {/* Note about COD availability */}
                {hasAudioOrOnline && (
                    <Text color="red.500" fontSize="sm" mt={2}>
                        Lưu ý: Không thể thanh toán COD cho sản phẩm loại AUDIO hoặc ONLINE.
                    </Text>
                )}
            </VStack>

            <Divider my={6} />

            {/* Tổng tiền */}
            <Flex justify="space-between" align="center">
                <Text fontWeight="bold" fontSize="xl">Tổng số tiền:</Text>
                <Text color="teal.600" fontWeight="bold" fontSize="2xl">{formatCurrency(discountedTotal)}</Text>
            </Flex>

            <Divider my={6} />

            {/* Chọn phương thức thanh toán */}
            <VStack spacing={4} align="stretch">
                <Text fontWeight="bold" fontSize="lg">Tùy chọn thanh toán</Text>

                <Button
                    leftIcon={<Icon as={FaMoneyBillWave} />}
                    colorScheme="green"
                    variant="solid"
                    onClick={() => handlePayment('COD')}
                >
                    Thanh toán khi nhận hàng (COD)
                </Button>

                <Button
                    leftIcon={<Icon as={FaCreditCard} />}
                    colorScheme="blue"
                    variant="outline"
                    onClick={() => handlePayment('VNPay')}
                >
                    Thanh toán qua VNPay
                </Button>

                <Text color="gray.600" fontSize="sm">
                    <Icon as={FaTruck} color="teal.500" /> Giao hàng tiêu chuẩn mất 3-5 ngày làm việc.
                </Text>
            </VStack>
        </Box>
    );
};

export default Checkout;
