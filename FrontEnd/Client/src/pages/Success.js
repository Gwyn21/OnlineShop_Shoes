import React, { useEffect } from 'react';
import { useToast, Box, Heading, Text, Button, VStack } from '@chakra-ui/react';
import { useNavigate } from 'react-router-dom';
import { useCart } from '../context/CartContext';
import orderApi from '../apis/orderApi';

const Success = () => {
    const toast = useToast();
    const navigate = useNavigate();
    const { clearCart } = useCart();

    useEffect(() => {
        const handleVNPayResponse = async () => {
            const urlParams = new URLSearchParams(window.location.search);
            const vnp_ResponseCode = urlParams.get('vnp_ResponseCode');
            const vnp_TransactionStatus = urlParams.get('vnp_TransactionStatus');
            const vnp_TxnRef = urlParams.get('vnp_TxnRef');

            // Kiểm tra kết quả thanh toán VNPay
            if (vnp_ResponseCode === '00' && vnp_TransactionStatus === '00') {
                try {
                    const orderData = JSON.parse(localStorage.getItem('orderData'));
                    if (orderData) {
                        await orderApi.createOrder(orderData);
                        console.log('Đơn hàng đã được tạo thành công:', orderData);
                        clearCart();
                        toast({
                            title: 'Thanh toán thành công',
                            description: 'Cảm ơn bạn đã đặt hàng!',
                            status: 'success',
                            duration: 5000,
                            isClosable: true,
                        });
                    }

                    // Xóa dữ liệu đơn hàng khỏi localStorage
                    localStorage.removeItem('orderData');
                } catch (error) {
                    console.error('Lỗi khi tạo đơn hàng:', error);
                    toast({
                        title: 'Lỗi',
                        description: 'Có lỗi xảy ra khi xử lý đơn hàng. Vui lòng liên hệ hỗ trợ.',
                        status: 'error',
                        duration: 5000,
                        isClosable: true,
                    });
                }
            } else {
                toast({
                    title: 'Thanh toán thất bại',
                    description: 'Có lỗi xảy ra trong quá trình thanh toán. Vui lòng thử lại.',
                    status: 'error',
                    duration: 5000,
                    isClosable: true,
                });
            }
        };

        handleVNPayResponse();
    }, [clearCart]);

    return (
        <Box p={8} maxW="600px" mx="auto" bg="white" boxShadow="xl" borderRadius="lg" textAlign="center" mt={16} mb={16}>
            <VStack spacing={4}>
                <Heading as="h1" size="xl" color="teal.600">Thanh Toán Thành Công!</Heading>
                <Text fontSize="lg" color="gray.600">Cảm ơn bạn đã đặt hàng. Đơn hàng của bạn đã được xử lý thành công!</Text>
                <Button 
                    colorScheme="teal" 
                    size="lg" 
                    onClick={() => navigate('/')}
                >
                    Quay lại trang chủ
                </Button>
            </VStack>
        </Box>
    );
};

export default Success; 