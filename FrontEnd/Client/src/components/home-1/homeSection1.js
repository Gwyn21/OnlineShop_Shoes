import React from 'react';
import { Box, Image, Flex, Container } from '@chakra-ui/react';
import Slider from 'react-slick';
import 'slick-carousel/slick/slick.css';
import 'slick-carousel/slick/slick-theme.css';

const HomeSection1 = () => {
    const settings = {
        dots: true,
        infinite: true,
        speed: 500,
        slidesToShow: 1,
        slidesToScroll: 1,
        autoplay: true,
        arrows: false,
    };

    return (
        <Container maxW="7xl" p={5}>
            <Flex justify="space-between">
                <Box textAlign="center" width="100%" position="relative" height="320px">
                    <Slider {...settings}>
                        <div>
                            <Image src="https://contents.mediadecathlon.com/s1217471/k$41c610a683a5d26fabdd11795f825e16/payday_mar_home_web_vi_v1_1x.webp" alt="Promo 1" borderRadius="md" height="100%" />
                        </div>
                        <div>
                            <Image src="https://contents.mediadecathlon.com/s1214267/k$bdf033a265c17cb24a092aae820118aa/CC-HB-web-vi.webp" alt="Promo 2" borderRadius="md" height="100%" />
                        </div>
                        <div>
                            <Image src="	https://contents.mediadecathlon.com/s1202332/k$b3fee357c9b155ab100b0c1ba5bb89f2/Group%209.webp" alt="Promo 3" borderRadius="md" height="100%" />
                        </div>
                    </Slider>
                </Box>
               
            </Flex>
        </Container>
    );
};

export default HomeSection1;
