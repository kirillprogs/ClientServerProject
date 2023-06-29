import React, {useEffect, useState} from 'react';

const Login = ({ onLogin }) => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [errorMessages, setErrorMessages] = useState(null);


    // User Login info for now
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

    // Fetch groups from the server
    const fetchGroups = () => {

        setGroups([
            { id: 1, name: 'Group 1' },
            { id: 2, name: 'Group 2' },
            { id: 3, name: 'Group 3' },
        ]);
    };

    // Delete a certain group
    const deleteGroup = (groupId) => {

        setGroups(groups.filter((group) => group.id !== groupId));
    };

    // Open a window for editing/updating a group
    const editGroup = (groupId) => {

        setSelectedGroup(groupId);
    };

    // Add a new group
    const addGroup = () => {

    };

    // Find a group by ID
    const findGroupById = () => {

    };

    // Render the group list
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
                    { }
                    <button onClick={() => setSelectedGroup(null)}>Close</button>
                </div>
            )}
        </div>
    );
};

const Products = ({ onLogout }) => {
    const [products, setProducts] = useState([]);

    // Fetch products from the server
    const fetchProducts = () => {

        setProducts([
            { id: 1, name: 'Product 1' },
            { id: 2, name: 'Product 2' },
            { id: 3, name: 'Product 3' },
        ]);
    };

    // Delete a certain product
    const deleteProduct = (productId) => {

        setProducts(products.filter((product) => product.id !== productId));
    };

    // Open a window for editing/updating a product
    const editProduct = (productId) => {

    };

    // Add a new product
    const addProduct = () => {

    };

    // Find a product by ID
    const findProductById = () => {

    };

    // Render the product list
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
