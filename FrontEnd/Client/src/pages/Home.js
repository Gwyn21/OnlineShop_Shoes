'use client'

import {
    Avatar,
    Box,
    Button,
    Container,
    Flex,
    Heading,
    Stack,
    Text,
    useColorModeValue
} from '@chakra-ui/react';
import React from 'react';
import HomeSection1 from '../components/home-1/homeSection1';
import PromotionalSection from '../components/home-2/PromotionalSection';
import HomeSection3 from '../components/home-3/product';

const Testimonial = ({ children }) => {
    return <Box>{children}</Box>;
};

const TestimonialContent = ({ children }) => {
    return (
        <Stack
            bg={useColorModeValue('white', 'gray.800')}
            boxShadow={'lg'}
            p={8}
            rounded={'xl'}
            align={'center'}
            pos={'relative'}
            _after={{
                content: `""`,
                w: 0,
                h: 0,
                borderLeft: 'solid transparent',
                borderLeftWidth: 16,
                borderRight: 'solid transparent',
                borderRightWidth: 16,
                borderTop: 'solid',
                borderTopWidth: 16,
                borderTopColor: useColorModeValue('white', 'gray.800'),
                pos: 'absolute',
                bottom: '-16px',
                left: '50%',
                transform: 'translateX(-50%)',
            }}>
            {children}
        </Stack>
    );
};

const TestimonialHeading = ({ children }) => {
    return (
        <Heading as={'h3'} fontSize={'xl'}>
            {children}
        </Heading>
    );
};

const TestimonialText = ({ children }) => {
    return (
        <Text
            textAlign={'center'}
            color={useColorModeValue('gray.600', 'gray.400')}
            fontSize={'sm'}>
            {children}
        </Text>
    );
};

const TestimonialAvatar = ({ src, name, title }) => {
    return (
        <Flex align={'center'} mt={8} direction={'column'}>
            <Avatar src={src} mb={2} />
            <Stack spacing={-1} align={'center'}>
                <Text fontWeight={600}>{name}</Text>
                <Text fontSize={'sm'} color={useColorModeValue('gray.600', 'gray.400')}>
                    {title}
                </Text>
            </Stack>
        </Flex>
    );
};

const Home = () => {
    return (
        <Box bg={useColorModeValue('gray.100', 'gray.700')}>

            <HomeSection1 />
            <PromotionalSection />
            <Container maxW="7xl" >
                <Box bg="red.100" p={4} borderRadius="md" mb={2}>
                    <Text fontSize="2xl" fontWeight="bold" color="red.600">
                        FLASH SALE
                    </Text>
                    <Text fontSize="sm" color="gray.600">
                        Kết thúc trong 00:02:09
                    </Text>
                </Box>
            </Container>
            <HomeSection3 />

            <Container maxW={'7xl'} py={16} as={Stack} spacing={12}>
                <Stack spacing={0} align={'center'}>
                    <Heading>Đánh giá từ khách hàng</Heading>
                    <Text>Chúng tôi tự hào về chất lượng sản phẩm và dịch vụ của mình</Text>
                </Stack>
                <Stack
                    direction={{ base: 'column', md: 'row' }}
                    spacing={{ base: 10, md: 4, lg: 10 }}>
                    <Testimonial>
                        <TestimonialContent>
                            <TestimonialHeading>Chất lượng tuyệt vời</TestimonialHeading>
                            <TestimonialText>
                                Giày Nike Air Max tôi mua rất bền và thoải mái. Dịch vụ giao hàng nhanh chóng, đóng gói cẩn thận.
                            </TestimonialText>
                        </TestimonialContent>
                        <TestimonialAvatar
                            src={
                                'https://images.unsplash.com/photo-1586297135537-94bc9ba060aa?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=100&q=80'
                            }
                            name={'Nguyễn Văn A'}
                            title={'Vận động viên chạy bộ'}
                        />
                    </Testimonial>
                    <Testimonial>
                        <TestimonialContent>
                            <TestimonialHeading>Đa dạng mẫu mã</TestimonialHeading>
                            <TestimonialText>
                                Website có nhiều lựa chọn giày từ các thương hiệu nổi tiếng. Tôi dễ dàng tìm được đôi giày bóng rổ phù hợp.
                            </TestimonialText>
                        </TestimonialContent>
                        <TestimonialAvatar
                            src={
                                'https://images.unsplash.com/photo-1586297135537-94bc9ba060aa?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=100&q=80'
                            }
                            name={'Trần Thị B'}
                            title={'Cầu thủ bóng rổ'}
                        />
                    </Testimonial>
                    <Testimonial>
                        <TestimonialContent>
                            <TestimonialHeading>Dịch vụ chuyên nghiệp</TestimonialHeading>
                            <TestimonialText>
                                Nhân viên tư vấn rất nhiệt tình, giúp tôi chọn được đôi giày lifestyle phù hợp với phong cách.
                            </TestimonialText>
                        </TestimonialContent>
                        <TestimonialAvatar
                            src={
                                'https://images.unsplash.com/photo-1586297135537-94bc9ba060aa?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=100&q=80'
                            }
                            name={'Lê Văn C'}
                            title={'Khách hàng thường xuyên'}
                        />
                    </Testimonial>
                </Stack>
            </Container>

        </Box>
    );
};

export default Home;