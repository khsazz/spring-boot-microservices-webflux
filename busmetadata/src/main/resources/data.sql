-- Create the BUS table if it doesn't exist
CREATE TABLE IF NOT EXISTS BUS (
                                   bus_id INT PRIMARY KEY,
                                   plate_number VARCHAR(255) NOT NULL
    );

-- Insert 10 entries if they don't already exist
INSERT INTO BUS (bus_id, plate_number)
SELECT * FROM (
                  SELECT 1 AS bus_id, 'BUS1' AS plate_number
                  UNION SELECT 2, 'BUS2'
                  UNION SELECT 3, 'BUS3'
                  UNION SELECT 4, 'BUS4'
                  UNION SELECT 5, 'BUS5'
                  UNION SELECT 6, 'BUS6'
                  UNION SELECT 7, 'BUS7'
                  UNION SELECT 8, 'BUS8'
                  UNION SELECT 9, 'BUS9'
                  UNION SELECT 10, 'BUS10'
              ) AS temp
WHERE NOT EXISTS (
    SELECT 1 FROM BUS WHERE BUS.bus_id = temp.bus_id
);
