import {
    DeleteOutlined,
    EditOutlined,
    PlusOutlined,
    ShoppingOutlined
} from '@ant-design/icons';
import { PageHeader } from '@ant-design/pro-layout';
import {
    BackTop,
    Breadcrumb,
    Button,
    Col,
    Form,
    Image,
    Input,
    Modal,
    Popconfirm,
    Row,
    Select,
    Space,
    Spin,
    Table,
    Tag,
    notification
} from 'antd';
import 'firebase/storage';
import { getDownloadURL, ref, uploadBytes } from 'firebase/storage';
import React, { useEffect, useState } from 'react';
import { useHistory } from 'react-router-dom';
import bookApi from '../../apis/bookApi';
import categoryApi from '../../apis/categoryApi';
import { storage } from '../../config/FirebaseConfig';
import "./productList.css";

const { Option } = Select;


const LANGUAGES = [
    "Da thật",
    "Da tổng hợp",
    "Vải canvas", 
    "Vải lưới (mesh)",
    "Vải dệt",
    "Cao su",
    "Nubuck",
    "Suede",
    "Da lộn",
    "Flyknit"
];

const SIZES = [
    "35", "35.5", "36", "36.5", "37", "37.5", "38", "38.5", "39", "39.5",
    "40", "40.5", "41", "41.5", "42", "42.5", "43", "43.5", "44", "44.5"
];

const COLORS = [
    "Đen", "Trắng", "Đỏ", "Xanh dương", "Xanh lá", "Vàng", "Hồng", "Tím", "Cam", "Xám",
    "Nâu", "Be", "Kem", "Bạc", "Đồng", "Hồng đất", "Xanh ngọc", "Xanh navy", "Đỏ đô", "Xanh rêu"
];

const PUBLISHERS = [
    "Nike",
    "Adidas", 
    "Puma",
    "New Balance",
    "Converse",
    "Vans",
    "Reebok",
    "Under Armour",
    "ASICS",
    "Jordan"
];

const ProductList = () => {
    const [products, setProducts] = useState([]);
    const [categories, setCategories] = useState([]);
    const [openModalCreate, setOpenModalCreate] = useState(false);
    const [openModalUpdate, setOpenModalUpdate] = useState(false);
    const [loading, setLoading] = useState(true);
    const [form] = Form.useForm();
    const [form2] = Form.useForm();
    const [id, setId] = useState();
    const [image, setImage] = useState();
    const [searchTerm, setSearchTerm] = useState("");

    const showModal = () => {
        setOpenModalCreate(true);
    };

    const createProduct = async (productData) => {
        try {
            const response = await bookApi.createProduct(productData);
            if (response) {
                notification["success"]({
                    message: `Thông báo`,
                    description: 'Tạo sản phẩm thành công',
                });
                handleProductList();
                setOpenModalCreate(false);
            }
        } catch (error) {
            notification["error"]({
                message: `Thông báo`,
                description: 'Tạo sản phẩm thất bại',
            });
        }
    }

    const handleProductList = async () => {
        try {
            const res = await bookApi.getListProducts();
            setProducts(res);
            setLoading(false);
        } catch (error) {
            console.log('Failed to fetch product list:' + error);
        }
    }

    const handleCategoryList = async () => {
        try {
            const res = await categoryApi.getListCategory();
            setCategories(res);
        } catch (error) {
            console.log('Failed to fetch category list:' + error);
        }
    }

    const handleCancel = () => {
        setOpenModalCreate(false);
        setOpenModalUpdate(false);
    };

    const handleOkUser = async (values) => {
        setLoading(true);
        const productData = {
            name: values.name,
            brand: values.brand,
            material: values.material,
            preview: values.preview,
            image: image,
            categories: {
                id: values.categoryId,
                name: categories.find(cat => cat.id === values.categoryId)?.name
            },
            description: values.description,
            price: values.price,
            stockQuantity: values.stockQuantity,
            status: values.status,
            productType: 'SNEAKER',
            size: values.size,
            color: values.color
        };
        await createProduct(productData);
        setLoading(false);
    }

    const handleEditProduct = async (productId) => {
        setOpenModalUpdate(true);
        try {
            const response = await bookApi.getDetailProduct(productId);
            setId(productId);
            form2.setFieldsValue({
                name: response.name,
                brand: response.brand,
                material: response.material,
                categoryId: response.categories.id,
                description: response.description,
                price: response.price,
                stockQuantity: response.stockQuantity,
                status: response.status,
                productType: response.productType,
                preview: response.preview,
                size: response.size,
                color: response.color
            });
            setImage(response.image)
        } catch (error) {
            notification["error"]({
                message: `Thông báo`,
                description: 'Lấy thông tin sản phẩm thất bại',
            });
        }
    };

    const handleUpdateProduct = async (values) => {
        setLoading(true);
        const productData = {
            name: values.name,
            brand: values.brand,
            material: values.material,
            preview: values.preview,
            image: image,
            categories: {
                id: values.categoryId,
                name: categories.find(cat => cat.id === values.categoryId)?.name
            },
            description: values.description,
            price: values.price,
            stockQuantity: values.stockQuantity,
            status: values.status,
            productType: 'SNEAKER',
            size: values.size,
            color: values.color
        };
        try {
            await bookApi.updateProduct(id, productData);
            notification["success"]({
                message: `Thông báo`,
                description: 'Cập nhật sản phẩm thành công',
            });
            handleProductList();
            setOpenModalUpdate(false);
        } catch (error) {
            notification["error"]({
                message: `Thông báo`,
                description: 'Cập nhật sản phẩm thất bại',
            });
        }
        setLoading(false);
    };

    const handleDeleteProduct = async (productId) => {
        setLoading(true);
        try {
            await bookApi.deleteProduct(productId);
            notification["success"]({
                message: `Thông báo`,
                description: 'Xóa sản phẩm thành công',
            });
            handleProductList();
            setLoading(false);
        } catch (error) {
            notification["error"]({
                message: `Thông báo`,
                description: 'Xóa sản phẩm thất bại',
            });
            setLoading(false);
        }
    };

    const handleChangeImage = async (e) => {
        setLoading(true);
        const file = e.target.files[0];
        if (file) {
            const storageRef = ref(storage, `images/${file.name}`);
            try {
                await uploadBytes(storageRef, file);
                const url = await getDownloadURL(storageRef);
                setImage(url);
            } catch (error) {
                console.error("Error uploading file:", error);
            }
        }
        setLoading(false);
    };

    const handleChangeFile = async (e) => {
        setLoading(true);
        const file = e.target.files[0];
        if (file) {
            const storageRef = ref(storage, `files/${file.name}`);
            try {
                await uploadBytes(storageRef, file);
                const url = await getDownloadURL(storageRef);
            } catch (error) {
                console.error("Error uploading file:", error);
            }
        }
        setLoading(false);
    };

    const handleSearch = (value) => {
        setSearchTerm(value);
    };

    const filteredProducts = products.filter(product => 
        product.name.toLowerCase().includes(searchTerm.toLowerCase())
    );
    const columns = [
        {
            title: 'ID',
            key: 'id',
            render: (text, record, index) => index + 1,
        },
        {
            title: 'Hình ảnh',
            dataIndex: 'image',
            key: 'image',
            render: (text) => <Image src={text} alt="product cover" style={{ width: 50, height: 50 }} />,
        },
        {
            title: 'Tên sản phẩm',
            dataIndex: 'name',
            key: 'name',
        },
        {
            title: 'Thương hiệu',
            dataIndex: 'brand',
            key: 'brand',
        },
        {
            title: 'Danh mục',
            dataIndex: ['categories', 'name'],
            key: 'category',
        },
        {
            title: 'Giá',
            dataIndex: 'price',
            key: 'price',
            render: (text) => new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(text),
        },
        {
            title: 'Số lượng tồn kho',
            dataIndex: 'stockQuantity',
            key: 'stockQuantity',
        },
        {
            title: 'Kích thước',
            dataIndex: 'size',
            key: 'size',
        },
        {
            title: 'Màu sắc',
            dataIndex: 'color',
            key: 'color',
        },
        {
            title: 'Trạng thái',
            dataIndex: 'status',
            key: 'status',
        },
        {
            title: 'Hành động',
            key: 'action',
            render: (text, record) => (
                <div style={{ display: 'flex', flexDirection: 'column' }}>
                    <Button
                        size="small"
                        icon={<EditOutlined />}
                        style={{ width: 150, borderRadius: 15, height: 30, marginBottom: 10 }}
                        onClick={() => handleEditProduct(record.id)}
                    >
                        {"Chỉnh sửa"}
                    </Button>
                    <Popconfirm
                        title="Bạn có chắc chắn xóa sản phẩm này?"
                        onConfirm={() => handleDeleteProduct(record.id)}
                        okText="Yes"
                        cancelText="No"
                    >
                        <Button
                            size="small"
                            icon={<DeleteOutlined />}
                            style={{ width: 150, borderRadius: 15, height: 30 }}
                        >
                            {"Xóa"}
                        </Button>
                    </Popconfirm>
                </div>
            ),
        }
    ];

    useEffect(() => {
        handleProductList();
        handleCategoryList();
    }, []);

    return (
        <div>
            <Spin spinning={loading}>
                <div className='container'>
                    <div style={{ marginTop: 20 }}>
                        <Breadcrumb>
                            <Breadcrumb.Item href="">
                                <ShoppingOutlined />
                            </Breadcrumb.Item>
                            <Breadcrumb.Item href="">
                                <span>Danh sách sản phẩm</span>
                            </Breadcrumb.Item>
                        </Breadcrumb>
                    </div>

                    <div style={{ marginTop: 20 }}>
                        <div id="my__event_container__list">
                            <PageHeader
                                subTitle=""
                                style={{ fontSize: 14 }}
                            >
                                <Row>
                                    <Col span="18">
                                        <Input
                                            placeholder="Tìm kiếm"
                                            allowClear
                                            onChange={(e) => handleSearch(e.target.value)}
                                            style={{ width: 300 }}
                                        />
                                    </Col>
                                    <Col span="6">
                                        <Row justify="end">
                                            <Space>
                                                <Button onClick={showModal} icon={<PlusOutlined />} style={{ marginLeft: 10 }} >Tạo Giày</Button>
                                            </Space>
                                        </Row>
                                    </Col>
                                </Row>
                            </PageHeader>
                        </div>
                    </div>

                    <div style={{ marginTop: 30 }}>
                        <Table columns={columns} pagination={{ position: ['bottomCenter'] }} dataSource={filteredProducts} />
                    </div>
                </div>

                <Modal
                    title="Tạo sản phẩm mới"
                    visible={openModalCreate}
                    style={{ top: 100 }}
                    onOk={() => {
                        form
                            .validateFields()
                            .then((values) => {
                                form.resetFields();
                                handleOkUser(values);
                            })
                            .catch((info) => {
                                console.log('Validate Failed:', info);
                            });
                    }}
                    onCancel={handleCancel}
                    okText="Hoàn thành"
                    cancelText="Hủy"
                    width={600}
                >
                    <Form
                        form={form}
                        name="productCreate"
                        layout="vertical"
                        scrollToFirstError
                    >
                        <Form.Item
                            name="name"
                            label="Tên sản phẩm"
                            rules={[{ required: true, message: 'Vui lòng nhập tên sản phẩm!' }]}
                        >
                            <Input placeholder="Tên sản phẩm" />
                        </Form.Item>
                        <Form.Item
                            name="brand"
                            label="Thương hiệu"
                            rules={[{ required: true, message: 'Vui lòng nhập thương hiệu!' }]}
                        >
                            <Input placeholder="Thương hiệu" />
                        </Form.Item>
                        <Form.Item
                            name="material"
                            label="Chất liệu"
                            rules={[{ required: true, message: 'Vui lòng chọn chất liệu!' }]}
                        >
                            <Select placeholder="Chọn chất liệu">
                                {LANGUAGES.map(lang => (
                                    <Option key={lang} value={lang}>{lang}</Option>
                                ))}
                            </Select>
                        </Form.Item>
                        <Form.Item
                            name="image"
                            label="Hình ảnh"
                            rules={[{ required: true, message: 'Vui lòng nhập đường dẫn hình ảnh!' }]}
                        >
                            <input type="file" onChange={handleChangeImage}
                                id="avatar" name="file"
                                accept="image/png, image/jpeg" />
                        </Form.Item>
                        <Form.Item
                            name="categoryId"
                            label="Danh mục"
                            rules={[{ required: true, message: 'Vui lòng chọn danh mục!' }]}
                        >
                            <Select placeholder="Chọn danh mục">
                                {categories.map(category => (
                                    <Option key={category.id} value={category.id}>{category.name}</Option>
                                ))}
                            </Select>
                        </Form.Item>
                        <Form.Item
                            name="description"
                            label="Mô tả"
                            rules={[{ required: true, message: 'Vui lòng nhập mô tả!' }]}
                        >
                            <Input.TextArea placeholder="Mô tả" />
                        </Form.Item>
                        <Form.Item
                            name="price"
                            label="Giá"
                            rules={[{ required: true, message: 'Vui lòng nhập giá!' }]}
                        >
                            <Input type="number" placeholder="Giá" />
                        </Form.Item>
                        <Form.Item
                            name="stockQuantity"
                            label="Số lượng tồn kho"
                            rules={[{ required: true, message: 'Vui lòng nhập số lượng tồn kho!' }]}
                        >
                            <Input type="number" placeholder="Số lượng tồn kho" />
                        </Form.Item>
                        <Form.Item
                            name="status"
                            label="Trạng thái"
                            rules={[{ required: true, message: 'Vui lòng chọn trạng thái!' }]}
                        >
                            <Select placeholder="Chọn trạng thái">
                                <Option value="ACTIVE">Kích hoạt</Option>
                                <Option value="INACTIVE">Không kích hoạt</Option>
                            </Select>
                        </Form.Item>
                        <Form.Item
                            name="productType"
                            label="Loại sản phẩm"
                            initialValue="SNEAKER"
                            hidden
                        >
                            <Input type="hidden" value="SNEAKER" />
                        </Form.Item>
                        <Form.Item
                            name="size"
                            label="Kích thước"
                            rules={[{ required: true, message: 'Vui lòng chọn kích thước!' }]}
                        >
                            <Select placeholder="Chọn kích thước">
                                {SIZES.map(size => (
                                    <Option key={size} value={size}>{size}</Option>
                                ))}
                            </Select>
                        </Form.Item>
                        <Form.Item
                            name="color"
                            label="Màu sắc"
                            rules={[{ required: true, message: 'Vui lòng chọn màu sắc!' }]}
                        >
                            <Select placeholder="Chọn màu sắc">
                                {COLORS.map(color => (
                                    <Option key={color} value={color}>{color}</Option>
                                ))}
                            </Select>
                        </Form.Item>
                    </Form>
                </Modal>

                <Modal
                    title="Chỉnh sửa sản phẩm"
                    visible={openModalUpdate}
                    style={{ top: 100 }}
                    onOk={() => {
                        form2
                            .validateFields()
                            .then((values) => {
                                form2.resetFields();
                                handleUpdateProduct(values);
                            })
                            .catch((info) => {
                                console.log('Validate Failed:', info);
                            });
                    }}
                    onCancel={handleCancel}
                    okText="Hoàn thành"
                    cancelText="Hủy"
                    width={600}
                >
                    <Form
                        form={form2}
                        name="productUpdate"
                        layout="vertical"
                        scrollToFirstError
                    >
                        <Form.Item
                            name="name"
                            label="Tên sản phẩm"
                            rules={[{ required: true, message: 'Vui lòng nhập tên sản phẩm!' }]}
                        >
                            <Input placeholder="Tên sản phẩm" />
                        </Form.Item>
                        <Form.Item
                            name="brand"
                            label="Thương hiệu"
                            rules={[{ required: true, message: 'Vui lòng nhập thương hiệu!' }]}
                        >
                            <Input placeholder="Thương hiệu" />
                        </Form.Item>
                        <Form.Item
                            name="material"
                            label="Chất liệu"
                            rules={[{ required: true, message: 'Vui lòng chọn chất liệu!' }]}
                        >
                            <Select placeholder="Chọn chất liệu">
                                {LANGUAGES.map(lang => (
                                    <Option key={lang} value={lang}>{lang}</Option>
                                ))}
                            </Select>
                        </Form.Item>
                        <Form.Item
                            name="image"
                            label="Hình ảnh"
                        >
                            <input type="file" onChange={handleChangeImage}
                                id="avatar" name="file"
                                accept="image/png, image/jpeg" />
                        </Form.Item>
                        <Form.Item
                            name="categoryId"
                            label="Danh mục"
                            rules={[{ required: true, message: 'Vui lòng chọn danh mục!' }]}
                        >
                            <Select placeholder="Chọn danh mục">
                                {categories.map(category => (
                                    <Option key={category.id} value={category.id}>{category.name}</Option>
                                ))}
                            </Select>
                        </Form.Item>
                        <Form.Item
                            name="description"
                            label="Mô tả"
                            rules={[{ required: true, message: 'Vui lòng nhập mô tả!' }]}
                        >
                            <Input.TextArea placeholder="Mô tả" />
                        </Form.Item>
                        <Form.Item
                            name="price"
                            label="Giá"
                            rules={[{ required: true, message: 'Vui lòng nhập giá!' }]}
                        >
                            <Input type="number" placeholder="Giá" />
                        </Form.Item>
                        <Form.Item
                            name="stockQuantity"
                            label="Số lượng tồn kho"
                            rules={[{ required: true, message: 'Vui lòng nhập số lượng tồn kho!' }]}
                        >
                            <Input type="number" placeholder="Số lượng tồn kho" />
                        </Form.Item>
                        <Form.Item
                            name="status"
                            label="Trạng thái"
                            rules={[{ required: true, message: 'Vui lòng chọn trạng thái!' }]}
                        >
                            <Select placeholder="Chọn trạng thái">
                                <Option value="ACTIVE">Kích hoạt</Option>
                                <Option value="INACTIVE">Không kích hoạt</Option>
                            </Select>
                        </Form.Item>
                        <Form.Item
                            name="productType"
                            label="Loại sản phẩm"
                            initialValue="SNEAKER"
                            hidden
                        >
                            <Input type="hidden" value="SNEAKER" />
                        </Form.Item>
                        <Form.Item
                            name="size"
                            label="Kích thước"
                            rules={[{ required: true, message: 'Vui lòng chọn kích thước!' }]}
                        >
                            <Select placeholder="Chọn kích thước">
                                {SIZES.map(size => (
                                    <Option key={size} value={size}>{size}</Option>
                                ))}
                            </Select>
                        </Form.Item>
                        <Form.Item
                            name="color"
                            label="Màu sắc"
                            rules={[{ required: true, message: 'Vui lòng chọn màu sắc!' }]}
                        >
                            <Select placeholder="Chọn màu sắc">
                                {COLORS.map(color => (
                                    <Option key={color} value={color}>{color}</Option>
                                ))}
                            </Select>
                        </Form.Item>
                    </Form>
                </Modal>

                <BackTop style={{ textAlign: 'right' }} />
            </Spin>
        </div>
    )
}

export default ProductList;