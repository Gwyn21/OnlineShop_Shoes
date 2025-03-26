import React, { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import {
  Box,
  Image,
  Text,
  VStack,
  HStack,
  Button,
  Divider,
  Breadcrumb,
  BreadcrumbItem,
  BreadcrumbLink,
  Badge,
  Grid,
  GridItem,
  Flex,
  Spacer,
  useToast,
  NumberInput,
  NumberInputField,
  NumberInputStepper,
  NumberIncrementStepper,
  NumberDecrementStepper,
  Textarea,
} from "@chakra-ui/react";
import { FiShoppingCart } from "react-icons/fi";
import productApi from '../apis/productApi';
import reviewApi from "../apis/reviewApi";
import { StarIcon } from "@chakra-ui/icons";
import { useCart } from '../context/CartContext';
import userApi from "../apis/userApi";

const formatPrice = (price) => {
  return price.toLocaleString('vi-VN', { style: 'currency', currency: 'VND' });
};

const ProductDetail = () => {
  const { id } = useParams();
  const [product, setProduct] = useState(null);
  const [quantity, setQuantity] = useState(1);
  const toast = useToast();
  const { addToCart } = useCart();
  const [reviews, setReviews] = useState([]);
  const [newReview, setNewReview] = useState({ rating: 0, comment: "" });
  const [users, setUsers] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    productApi.getDetailProduct(id).then((response) => {
      setProduct(response);
    });

    reviewApi.getReviewsByProductId(id).then((response) => {
      setReviews(response);
    });

    userApi.getAllUsers().then((response) => {
      setUsers(response);
    });
  }, [id]);

  const handleAddToCart = () => {
    addToCart(product, quantity);
    toast({
      title: "Đã thêm vào giỏ hàng",
      description: `${product.name} đã được thêm vào giỏ hàng.`,
      status: "success",
      duration: 3000,
      isClosable: true,
    });
  };

  const handleBuyNow = () => {
    addToCart(product, quantity);
    navigate('/cart');
    toast({
      title: "Mua ngay",
      description: `Đã thêm ${product.name}.`,
      status: "success",
      duration: 3000,
      isClosable: true,
    });
  };

  const handleReviewSubmit = () => {
    const user = JSON.parse(localStorage.getItem('user'));
    if (!user) {
      toast({
        title: "Lỗi",
        description: "Bạn cần đăng nhập để gửi đánh giá.",
        status: "error",
        duration: 3000,
        isClosable: true,
      });
      return;
    }

    if (newReview.rating === 0 || newReview.comment.trim() === "") {
      toast({
        title: "Lỗi",
        description: "Vui lòng chọn đánh giá và nhập bình luận.",
        status: "error",
        duration: 3000,
        isClosable: true,
      });
      return;
    }

    reviewApi.createReview({ productId: id, userId: user.id, ...newReview }).then(() => {
      toast({
        title: "Đánh giá đã được gửi",
        description: "Cảm ơn bạn đã đánh giá.",
        status: "success",
        duration: 3000,
        isClosable: true,
      });
      // Refresh reviews
      reviewApi.getReviewsByProductId(id).then((response) => {
        setReviews(response);
      });
      // Reset review form
      setNewReview({ rating: 0, comment: "" });
    });
  };

  const getUsernameById = (userId) => {
    const user = users.find((user) => user.id === userId);
    return user ? user.username : "Unknown User";
  };

  if (!product) return <Text>Loading...</Text>;

  return (
    <Box p={5} maxW="1100px" mx="auto" bg="white" boxShadow="xl" borderRadius="lg">
      {/* Breadcrumb */}
      <Breadcrumb mb={5} fontSize="sm" color="gray.600">
        <BreadcrumbItem>
          <BreadcrumbLink href="/">Trang chủ</BreadcrumbLink>
        </BreadcrumbItem>
        <BreadcrumbItem>
          <BreadcrumbLink href="/products">Sản phẩm</BreadcrumbLink>
        </BreadcrumbItem>
        <BreadcrumbItem isCurrentPage>
          <BreadcrumbLink href="#">{product.name}</BreadcrumbLink>
        </BreadcrumbItem>
      </Breadcrumb>

      {/* Layout: Image & Details */}
      <Grid templateColumns={["1fr", "1fr 2fr"]} gap={8}>
        {/* Image Section */}
        <GridItem>
          <Image
            src={product.image}
            alt={product.name}
            borderRadius="lg"
            boxSize="100%"
            objectFit="cover"
            boxShadow="md"
          />
        </GridItem>

        {/* Details Section */}
        <GridItem>
          <VStack spacing={6} align="start">
            <Text fontWeight="bold" fontSize="3xl" color="gray.800">
              {product.name}
            </Text>
            <HStack spacing={4}>
              <Text fontSize="lg" color="gray.600">
                Thương hiệu: <strong>{product.brand}</strong>
              </Text>
              <Spacer />
              <Text fontSize="lg" color="gray.600">
                Danh mục: <strong>{product.categories.name}</strong>
              </Text>
            </HStack>

            <HStack spacing={3}>
              <Badge colorScheme="teal" variant="solid">Màu: {product.color}</Badge>
              <Badge colorScheme="purple" variant="solid">Size: {product.size}</Badge>
              <Badge colorScheme="blue" variant="solid">Loại: {product.productType}</Badge>
            </HStack>

            <Text fontSize="lg" color="gray.700" lineHeight="tall">
              {product.description}
            </Text>

            <Text color="red.500" fontWeight="bold" fontSize="4xl">
              {formatPrice(product.price)}
            </Text>

            <VStack w="full" pt={6} alignItems="start" spacing={4}>
              <Flex w="full" alignItems="center" justify="space-between">
                <NumberInput
                  size="lg"
                  maxW={28}
                  defaultValue={1}
                  min={1}
                  value={quantity}
                  onChange={(value) => setQuantity(parseInt(value))}
                  mr={6}
                >
                  <NumberInputField />
                  <NumberInputStepper>
                    <NumberIncrementStepper />
                    <NumberDecrementStepper />
                  </NumberInputStepper>
                </NumberInput>
              </Flex>
              <Flex w="full" alignItems="center" justify="space-between">
                <Button
                  leftIcon={<FiShoppingCart />}
                  colorScheme="red"
                  size="lg"
                  flex={1}
                  mr={6}
                  _hover={{ bg: "red.600" }}
                  width="full"
                  onClick={handleAddToCart}
                >
                  Thêm vào giỏ hàng
                </Button>
                <Button
                  colorScheme="red"
                  size="lg"
                  flex={1}
                  mr={6}
                  _hover={{ bg: "red.600" }}
                  width="full"
                  onClick={handleBuyNow}
                >
                  Mua ngay
                </Button>
              </Flex>
            </VStack>
          </VStack>
        </GridItem>
      </Grid>

      {/* Divider */}
      <Divider my={5} />

      {/* Additional Info */}
      <Box mt={6} p={4} borderWidth={1} borderRadius="md" boxShadow="md" bg="white">
        <Text fontWeight="bold" fontSize="2xl" mb={4} color="teal.600">Thông tin chi tiết:</Text>
        <Grid templateColumns="repeat(2, 1fr)" gap={6}>
          <GridItem>
            <Text fontSize="lg" color="gray.700">Chất liệu: <strong>{product.material}</strong></Text>
          </GridItem>
          <GridItem>
            <Text fontSize="lg" color="gray.700">Số lượng tồn kho: <strong>{product.stockQuantity}</strong></Text>
          </GridItem>
          <GridItem>
            <Text fontSize="lg" color="gray.700">Loại sản phẩm: <strong>{product.productType}</strong></Text>
          </GridItem>
          <GridItem>
            <Text fontSize="lg" color="gray.700">Danh mục: <strong>{product.categories.name}</strong></Text>
          </GridItem>
        </Grid>
      </Box>

      {/* Reviews Section */}
      <Box mt={6} p={4} borderWidth={1} borderRadius="md" boxShadow="md" bg="white">
        <Text fontWeight="bold" fontSize="2xl" mb={4} color="teal.600">Đánh giá:</Text>
        <VStack spacing={4} align="start">
          {reviews.map((review) => (
            <Box key={review.id} p={4} borderWidth={1} borderRadius="md" boxShadow="md" bg="white" width="100%">
              <Text fontWeight="bold" color="teal.600">Họ tên: {getUsernameById(review.userId)}</Text>
              <HStack spacing={1}>
                {[...Array(5)].map((_, i) => (
                  <StarIcon key={i} color={i < review.rating ? "teal.500" : "gray.300"} />
                ))}
              </HStack>
              <Text fontSize="lg" color="gray.700" mt={2}>{review.comment}</Text>
            </Box>
          ))}
        </VStack>
        <Divider my={4} />
        <VStack spacing={4} align="start">
          <Text fontWeight="bold" fontSize="lg">Viết đánh giá của bạn:</Text>
          <HStack>
            {[...Array(5)].map((_, i) => (
              <StarIcon
                key={i}
                color={i < newReview.rating ? "teal.500" : "gray.300"}
                cursor="pointer"
                onClick={() => setNewReview({ ...newReview, rating: i + 1 })}
              />
            ))}
          </HStack>
          <Textarea
            placeholder="Viết bình luận của bạn..."
            value={newReview.comment}
            onChange={(e) => setNewReview({ ...newReview, comment: e.target.value })}
          />
          <Button colorScheme="teal" onClick={handleReviewSubmit}>Gửi đánh giá</Button>
        </VStack>
      </Box>
    </Box>
  );
};

export default ProductDetail;
