import React, { useEffect, useState } from 'react';
import {
  Box, Heading, Table, Thead, Tbody, Tr, Th, Td, TableContainer, Link, Badge, Text,
  Breadcrumb, BreadcrumbItem, BreadcrumbLink, Icon, VStack, HStack,
} from '@chakra-ui/react';
import { FaShoePrints, FaLink, FaUser, FaTags, FaMoneyBillWave, FaCalendarAlt } from 'react-icons/fa';
import { ChevronRightIcon } from '@chakra-ui/icons';
import productApi from '../apis/productApi';
import { useNavigate } from 'react-router-dom';

const PurchasedBooks = () => {
  const [purchasedProducts, setPurchasedProducts] = useState([]);
  const userId = JSON.parse(localStorage.getItem("user"))?.id;
  const navigate = useNavigate();

  useEffect(() => {
    const fetchPurchasedProducts = async () => {
      if (userId) {
        const response = await productApi.getPurchasedProductsByUser(userId);
        setPurchasedProducts(response);
      }
    };
    fetchPurchasedProducts();
  }, [userId]);

  return (
    <Box p={8} bg="gray.50" minH="100vh">
      {/* Breadcrumb */}
      <Breadcrumb spacing="8px" separator={<ChevronRightIcon color="gray.500" />} mb={8}>
        <BreadcrumbItem>
          <BreadcrumbLink href="/">Trang Chủ</BreadcrumbLink>
        </BreadcrumbItem>
        <BreadcrumbItem isCurrentPage>
          <BreadcrumbLink href="#">Sản Phẩm Đã Mua</BreadcrumbLink>
        </BreadcrumbItem>
      </Breadcrumb>

      {/* Heading */}
      <Heading as="h2" mb={8} textAlign="center" color="teal.600">
        Danh Sách Sản Phẩm Đã Mua
      </Heading>

      {/* Description */}
      <Text mb={8} textAlign="center" fontSize="lg" color="gray.600">
        Đây là danh sách các sản phẩm bạn đã mua. Cảm ơn bạn đã tin tưởng KickzHub!
      </Text>

      {/* Product Table */}
      {purchasedProducts.length > 0 ? (
        <TableContainer border="1px" borderRadius="lg" boxShadow="lg" bg="white">
          <Table variant="simple" colorScheme="teal">
            <Thead>
              <Tr>
                <Th><Icon as={FaShoePrints} mr={2} /> Ảnh Sản Phẩm</Th>
                <Th><Icon as={FaShoePrints} mr={2} /> Tên Sản Phẩm</Th>
                <Th><Icon as={FaUser} mr={2} /> Thương Hiệu</Th>
                <Th><Icon as={FaTags} mr={2} /> Danh Mục</Th>
                <Th><Icon as={FaTags} mr={2} /> Loại Sản Phẩm</Th>
                <Th><Icon as={FaMoneyBillWave} mr={2} /> Giá</Th>
              </Tr>
            </Thead>
            <Tbody>
              {purchasedProducts.map((product) => (
                <Tr key={product.id}>
                  <Td>
                    <img
                      src={product.image}
                      alt={product.name}
                      width="100"
                      height="100"
                      style={{ borderRadius: "8px", objectFit: "cover" }}
                    />
                  </Td>
                  <Td fontWeight="bold" color="teal.800">{product.name}</Td>
                  <Td>{product.brand}</Td>
                  <Td>
                    <Badge colorScheme="teal" px={3} py={1} borderRadius="full">
                      {product.categories.name}
                    </Badge>
                  </Td>
                  <Td>
                    <Badge colorScheme="teal" px={3} py={1} borderRadius="full">
                      {product.productType}
                    </Badge>
                  </Td>
                  <Td>{new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(product.price)}</Td>
                </Tr>
              ))}
            </Tbody>
          </Table>
        </TableContainer>
      ) : (
        <Text mt={8} textAlign="center" color="gray.600" fontSize="lg">
          Không có sản phẩm nào được mua.
        </Text>
      )}

      {/* Footer */}
      <VStack mt={16} spacing={4} align="center">
        <Text fontSize="md" color="gray.500">
          Cảm ơn bạn đã tin tưởng KickzHub.
        </Text>
        <HStack>
          <Link href="/" color="teal.600" fontWeight="bold">
            Quay Lại Trang Chủ
          </Link>
          <Text color="gray.500">|</Text>
          <Link href="/help" color="teal.600" fontWeight="bold">
            Trợ Giúp
          </Link>
        </HStack>
      </VStack>
    </Box>
  );
};

export default PurchasedBooks;
