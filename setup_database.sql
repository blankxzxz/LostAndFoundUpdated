-- Create the lost_found database and items table
CREATE DATABASE IF NOT EXISTS lost_found;
USE lost_found;

-- Create items table
CREATE TABLE IF NOT EXISTS items (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    found_by VARCHAR(255) NOT NULL,
    date_found VARCHAR(20) NOT NULL,
    description TEXT,
    approved BOOLEAN DEFAULT FALSE
);

-- Insert some test data
INSERT INTO items (name, found_by, date_found, description, approved) VALUES
('Laptop', 'John Doe', '2024-01-15', 'Black Dell laptop found in library', FALSE),
('Wallet', 'Jane Smith', '2024-01-20', 'Brown leather wallet with ID cards', FALSE),
('Phone', 'Bob Johnson', '2024-01-10', 'iPhone 12 in blue case', TRUE),
('Keys', 'Alice Brown', '2024-01-25', 'Car keys with Toyota keychain', FALSE),
('Backpack', 'Charlie Wilson', '2024-01-18', 'Blue Nike backpack with books', TRUE),
('Watch', 'Diana Davis', '2024-01-22', 'Silver wristwatch, no brand visible', FALSE);