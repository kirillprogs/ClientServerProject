import React, {useEffect, useState} from 'react';

const Login = ({ onLogin }) => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [errorMessages, setErrorMessages] = useState(null);


    // User Login info
    const database = [
        {
            username: "user1",
            password: "pass1"
        },
        {
            username: "user2",
            password: "pass2"
        }
    ];

    const errors = {
        uname: "Invalid username",
        pass: "Invalid password"
    };

    // Generate JSX code for error message
    const renderErrorMessage = (name) =>
        name === errorMessages.name && (
            <div className="error">{errorMessages.message}</div>
        );


        const handleSubmit = (event) => {
            event.preventDefault();

            const { uname, pass } = event.target.elements;

            // Find user login info
            const userData = database.find((user) => user.username === uname.value);

            // Compare user info
            if (userData) {
                if (userData.password !== pass.value) {
                    // If password is invalid
                    setErrorMessages({ name: 'pass', message: errors.pass });
                } else {
                    onLogin();
                }
            } else {
                // If username not found
                setErrorMessages({ name: 'uname', message: errors.uname });
            }
        };

        return (
            <div>
                <h2>Login</h2>
                <form onSubmit={handleSubmit}>
                    <input
                        type="text"
                        name="uname"
                        placeholder="Username"
                        value={username}
                        onChange={(e) => setUsername(e.target.value)}
                    />
                    <input
                        type="password"
                        name="pass"
                        placeholder="Password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                    />
                    {errorMessages && (
                        <div>
                            <p>{errorMessages.message}</p>
                        </div>
                    )}
                    <button type="submit">Login</button>
                </form>
            </div>
        );
};

    const Groups = ({ onLogout }) => {
    const [groups, setGroups] = useState([]);
    const [selectedGroup, setSelectedGroup] = useState(null);

    // Function to fetch groups from the server
    const fetchGroups = () => {
        // Make an API call to retrieve the list of groups
        // Update the `groups` state with the response data
        setGroups([
            { id: 1, name: 'Group 1' },
            { id: 2, name: 'Group 2' },
            { id: 3, name: 'Group 3' },
        ]);
    };

    // Function to delete a group
    const deleteGroup = (groupId) => {
        // Make an API call to delete the group with the given `groupId`
        // Update the `groups` state by removing the deleted group
        setGroups(groups.filter((group) => group.id !== groupId));
    };

    // Function to open a modal for editing/updating a group
    const editGroup = (groupId) => {
        // Logic to open a modal or perform any other action for editing/updating the group
        setSelectedGroup(groupId);
    };

    // Function to add a new group
    const addGroup = () => {
        // Logic to open a modal or perform any other action for adding a new group
    };

    // Function to find a group by ID
    const findGroupById = () => {
        // Logic to open a modal or perform any other action for finding a group by ID
    };

    // Function to render the group list
    const renderGroupList = () => {
        return groups.map((group) => (
            <tr key={group.id}>
                <td>{group.id}</td>
                <td>{group.name}</td>
                <td>
                    <button onClick={() => deleteGroup(group.id)}>Delete</button>
                    <button onClick={() => editGroup(group.id)}>Update</button>
                </td>
            </tr>
        ));
    };

    // Fetch groups when the component mounts
    useEffect(() => {
        fetchGroups();
    }, []);

    return (
        <div>
            <h2>Groups</h2>
            <button onClick={onLogout}>Logout</button>
            <button onClick={fetchGroups}>Refresh</button>
            <button onClick={addGroup}>Add</button>
            <button onClick={findGroupById}>Find</button>
            <table>
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Actions</th>
                </tr>
                </thead>
                <tbody>{renderGroupList()}</tbody>
            </table>
            {selectedGroup && (
                <div>
                    {/* Modal or window for editing/updating the selected group */}
                    <button onClick={() => setSelectedGroup(null)}>Close</button>
                </div>
            )}
        </div>
    );
};

const Products = ({ onLogout }) => {
    const [products, setProducts] = useState([]);

    // Function to fetch products from the server
    const fetchProducts = () => {
        // Make an API call to retrieve the list of products
        // Update the `products` state with the response data
        setProducts([
            { id: 1, name: 'Product 1' },
            { id: 2, name: 'Product 2' },
            { id: 3, name: 'Product 3' },
        ]);
    };

    // Function to delete a product
    const deleteProduct = (productId) => {
        // Make an API call to delete the product with the given `productId`
        // Update the `products` state by removing the deleted product
        setProducts(products.filter((product) => product.id !== productId));
    };

    // Function to open a modal for editing/updating a product
    const editProduct = (productId) => {
        // Logic to open a modal or perform any other action for editing/updating the product
    };

    // Function to add a new product
    const addProduct = () => {
        // Logic to open a modal or perform any other action for adding a new product
    };

    // Function to find a product by ID
    const findProductById = () => {
        // Logic to open a modal or perform any other action for finding a product by ID
    };

    // Function to render the product list
    const renderProductList = () => {
        return products.map((product) => (
            <tr key={product.id}>
                <td>{product.id}</td>
                <td>{product.name}</td>
                <td>
                    <button onClick={() => deleteProduct(product.id)}>Delete</button>
                    <button onClick={() => editProduct(product.id)}>Update</button>
                </td>
            </tr>
        ));
    };

    // Fetch products when the component mounts
    useEffect(() => {
        fetchProducts();
    }, []);

    return (
        <div>
            <h2>Products</h2>
            <button onClick={onLogout}>Logout</button>
            <button onClick={fetchProducts}>Refresh</button>
            <button onClick={addProduct}>Add</button>
            <button onClick={findProductById}>Find</button>
            <table>
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Actions</th>
                </tr>
                </thead>
                <tbody>{renderProductList()}</tbody>
            </table>
        </div>
    );
};

const App = () => {
    const [loggedIn, setLoggedIn] = useState(false);


    const handleLogin = () => {
        setLoggedIn(true);
    };

    const handleLogout = () => {
        setLoggedIn(false);
    };

    return (
        <div>
            {loggedIn ? (
                <Groups onLogout={handleLogout} />
            ) : (
                <Login onLogin={handleLogin} />
            )}
        </div>
    );
};

export default App;
