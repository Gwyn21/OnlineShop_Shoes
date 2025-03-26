import React from 'react';
import { Box, Container, Heading, Text, Stack, Image, Divider } from '@chakra-ui/react';

const About = () => {
  return (
    <Box bg="gray.50" color="gray.700" py={10}>
      <Container maxW="6xl">
        <Stack spacing={8} align="center">
          <Heading as="h1" size="2xl" textAlign="center" color="blue.600">
            Giới thiệu về KickzHub
          </Heading>
          <Image
            src="https://images.unsplash.com/photo-1542291026-7eec264c27ff?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1170&q=80"
            alt="KickzHub"
            borderRadius="md"
            boxShadow="lg"
            maxW="80%"
          />
          <Divider borderColor="gray.300" />
          <Text fontSize="lg" textAlign="center" maxW="3xl">
            Chào mừng bạn đến với KickzHub, trang web bán giày trực tuyến hàng đầu. Chúng tôi cung cấp một bộ sưu tập đa dạng các loại giày từ nhiều thương hiệu nổi tiếng, từ giày thể thao đến giày thời trang, từ giày chạy bộ đến giày bóng rổ. Sứ mệnh của chúng tôi là mang đến cho bạn những sản phẩm chất lượng cao với giá cả hợp lý.
          </Text>
          <Text fontSize="lg" textAlign="center" maxW="3xl">
            Tại KickzHub, chúng tôi tin rằng một đôi giày tốt không chỉ là phụ kiện thời trang mà còn là người bạn đồng hành đáng tin cậy trong mọi hoạt động. Hãy cùng chúng tôi khám phá thế giới giày và tìm kiếm đôi giày phù hợp với phong cách của bạn.
          </Text>
          <Divider borderColor="gray.300" />
          <Text fontSize="md" textAlign="center" color="gray.500">
            "Một đôi giày tốt có thể đưa bạn đến những nơi tuyệt vời." - KickzHub
          </Text>
        </Stack>
      </Container>
    </Box>
  );
};

export default About; 