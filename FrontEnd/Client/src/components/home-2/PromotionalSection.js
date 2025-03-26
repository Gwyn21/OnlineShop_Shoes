import React from 'react';
import { Box, Image, Text, Button, Flex, Container, VStack, Heading } from '@chakra-ui/react';
import { FaRunning, FaBasketballBall, FaShoePrints, FaTshirt } from 'react-icons/fa';

const PromotionalSection = () => {
    return (
        <Container maxW="7xl" p={5} bg="#f8f8f8">
            <Flex justify="space-between" gap={4}>
                <Box 
                    textAlign="center" 
                    width="23%" 
                    position="relative"
                    bg="white"
                    p={4}
                    borderRadius="lg"
                    boxShadow="md"
                    _hover={{ transform: 'translateY(-5px)', transition: 'all 0.3s' }}
                >
                    <VStack spacing={3}>
                        <Box p={3} bg="blue.100" borderRadius="full">
                            <FaRunning size={30} color="#3182CE" />
                        </Box>
                        <Heading size="md" color="blue.600">Giày Chạy Bộ</Heading>
                        <Text fontSize="sm" color="gray.600">Giảm đến 30%</Text>
                     
                    </VStack>
                </Box>

                <Box 
                    textAlign="center" 
                    width="23%" 
                    position="relative"
                    bg="white"
                    p={4}
                    borderRadius="lg"
                    boxShadow="md"
                    _hover={{ transform: 'translateY(-5px)', transition: 'all 0.3s' }}
                >
                    <VStack spacing={3}>
                        <Box p={3} bg="orange.100" borderRadius="full">
                            <FaBasketballBall size={30} color="#DD6B20" />
                        </Box>
                        <Heading size="md" color="orange.500">Giày Bóng Rổ</Heading>
                        <Text fontSize="sm" color="gray.600">Giảm đến 25%</Text>
                      
                    </VStack>
                </Box>

                <Box 
                    textAlign="center" 
                    width="23%" 
                    position="relative"
                    bg="white"
                    p={4}
                    borderRadius="lg"
                    boxShadow="md"
                    _hover={{ transform: 'translateY(-5px)', transition: 'all 0.3s' }}
                >
                    <VStack spacing={3}>
                        <Box p={3} bg="green.100" borderRadius="full">
                            <FaShoePrints size={30} color="#38A169" />
                        </Box>
                        <Heading size="md" color="green.500">Giày Lifestyle</Heading>
                        <Text fontSize="sm" color="gray.600">Giảm đến 20%</Text>
                       
                    </VStack>
                </Box>

                <Box 
                    textAlign="center" 
                    width="23%" 
                    position="relative"
                    bg="white"
                    p={4}
                    borderRadius="lg"
                    boxShadow="md"
                    _hover={{ transform: 'translateY(-5px)', transition: 'all 0.3s' }}
                >
                    <VStack spacing={3}>
                        <Box p={3} bg="purple.100" borderRadius="full">
                            <FaTshirt size={30} color="#805AD5" />
                        </Box>
                        <Heading size="md" color="purple.500">Phụ Kiện</Heading>
                        <Text fontSize="sm" color="gray.600">Giảm đến 15%</Text>
                    
                    </VStack>
                </Box>
            </Flex>
        </Container>
    );
};

export default PromotionalSection;
