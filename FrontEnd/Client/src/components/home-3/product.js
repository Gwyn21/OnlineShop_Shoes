import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { Box, Image, Text, Container, SimpleGrid, Badge, VStack, HStack } from '@chakra-ui/react';
import categoryApi from '../../apis/categoryApi';
import productApi from '../../apis/productApi';

const Product = () => {
    const [categories, setCategories] = useState([]);
    const [productsByCategory, setProductsByCategory] = useState({});
    const navigate = useNavigate();

    useEffect(() => {
        // Fetch categories
        categoryApi.getListCategory().then(response => {
            setCategories(response);
            response.forEach(category => {
                // Fetch products for each category
                productApi.getProductsByCategory(category.id).then(productResponse => {
                    // Filter out products with status 'INACTIVE'
                    const activeProducts = productResponse.filter(product => product.status !== 'INACTIVE');
                    setProductsByCategory(prevState => ({
                        ...prevState,
                        [category.id]: activeProducts
                    }));
                });
            });
        });
    }, []);

    const handleProductClick = (productId) => {
        navigate(`/product/${productId}`);
    };

    const formatPriceToVND = (price) => {
        return (price).toLocaleString('vi-VN', { style: 'currency', currency: 'VND' });
    };

    return (
        <Container maxW="7xl" py={5}>
            {categories.map(category => {
                const products = productsByCategory[category.id] || [];

                // Only render the category if there are products
                if (products.length === 0) return null;

                return (
                    <Box key={category.id} mb={10}>
                        <Box bg="gray.100" py={4} borderRadius="md" boxShadow="sm" mb={4} textAlign="center">
                            <Text fontSize="2xl" fontWeight="bold" color="teal.600">
                                {category.name}
                            </Text>
                            <Text fontSize="md" color="gray.500">
                                Bộ sưu tập {category.name}
                            </Text>
                        </Box>
                        <SimpleGrid columns={{ base: 1, sm: 2, md: 3, lg: 4 }} spacing={6}>
                            {products.slice(0, 4).map(product => (
                                <Box
                                    key={product.id}
                                    p={5}
                                    bg="white"
                                    boxShadow="lg"
                                    borderRadius="lg"
                                    overflow="hidden"
                                    transition="transform 0.2s"
                                    _hover={{ transform: 'scale(1.05)' }}
                                    onClick={() => handleProductClick(product.id)}
                                    cursor="pointer"
                                >
                                    <VStack spacing={3} align="start">
                                        <Image 
                                            src={product.image} 
                                            alt={product.name} 
                                            borderRadius="md" 
                                            boxSize="200px" 
                                            objectFit="cover"
                                            w="100%"
                                            h="200px"
                                        />
                                        <Text fontWeight="bold" fontSize="lg" noOfLines={2}>{product.name}</Text>
                                        <Text fontSize="sm" color="gray.600">Thương hiệu: {product.brand}</Text>
                                        <HStack spacing={2}>
                                            <Badge colorScheme="green">{product.productType}</Badge>
                                            <Badge colorScheme="blue">{product.color}</Badge>
                                            <Badge colorScheme="purple">Size {product.size}</Badge>
                                        </HStack>
                                        <Text fontSize="sm" color="gray.600">Chất liệu: {product.material}</Text>
                                        <Text color="red.500" fontWeight="bold" fontSize="xl">{formatPriceToVND(product.price)}</Text>
                                    </VStack>
                                </Box>
                            ))}
                        </SimpleGrid>
                    </Box>
                );
            })}
        </Container>
    );
};

export default Product;
