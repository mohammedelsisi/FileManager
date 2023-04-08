SELECT User_table.user_id, username, training_id, training_date, count(*) as count
FROM User_table , Training_details
WHERE User_table.user_id=Training_details.user_id
GROUP BY  user_id, username, training_id, training_date
HAVING count(*) > 1
ORDER BY training_date DESC