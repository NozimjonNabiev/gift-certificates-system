INSERT INTO gift_certificate (name, description, price, duration, create_date, last_update_date) VALUES
                                                                                                     ('$50 Gift Voucher', 'A $50 voucher applicable towards any service or purchase at our store.', 50.00, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                                                     ('$100 Spa Gift Certificate', 'Indulge in a spa treatment of your choice with this $100 gift certificate.', 100.00, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                                                     ('$200 Restaurant Dining Gift Card', 'Enjoy a fine dining experience worth $200 at our restaurant.', 200.00, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                                                     ('$150 Adventure Activity Gift Voucher', 'Select from a range of adventure activities using this $150 gift voucher.', 150.00, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                                                     ('$75 Online Shopping Gift Card', 'Shop for your favorite items with a $75 gift card applicable online.', 75.00, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                                                     ('$250 Travel Gift Voucher', 'Plan your next vacation with a $250 travel voucher for select destinations.', 250.00, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                                                     ('$50 Entertainment Gift Card', 'Get tickets for concerts, movies, and events with this $50 entertainment gift card.', 50.00, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                                                     ('$300 Wellness Spa Package', 'Experience a deluxe spa package worth $300 with various treatments and services.', 300.00, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                                                     ('$100 Hotel Stay Gift Certificate', 'Stay at a luxurious hotel using this $100 gift certificate for room accommodations.', 100.00, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                                                     ('$200 Fitness Club Membership Voucher', 'Avail a fitness club membership for a month worth $200 with this voucher.', 200.00, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                                                     ('$150 Shopping Mall Gift Card', 'Shop at multiple stores within the mall using this $150 gift card.', 150.00, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                                                     ('$75 Beauty Salon Gift Voucher', 'Treat yourself to beauty services and products worth $75 at our salon.', 75.00, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO tag (name)
VALUES
    ('Spa'),
    ('Dining'),
    ('Adventure'),
    ('Shopping'),
    ('Travel'),
    ('Entertainment'),
    ('Wellness'),
    ('Hotel'),
    ('Fitness'),
    ('Home'),
    ('Gaming'),
    ('Books');


INSERT INTO tag_gift_certificate (tag_id, gift_certificate_id)
VALUES
    (1, 1),  -- 'Spa' associated with '$50 Gift Voucher'
    (2, 2),  -- 'Dining' associated with '$100 Spa Gift Certificate'
    (3, 3),  -- 'Adventure' associated with '$200 Restaurant Dining Gift Card'
    (4, 4),  -- 'Shopping' associated with '$150 Adventure Activity Gift Voucher'
    (5, 5),  -- 'Travel' associated with '$75 Online Shopping Gift Card'
    (6, 6),  -- 'Entertainment' associated with '$250 Travel Gift Voucher'
    (7, 7),  -- 'Wellness' associated with '$50 Entertainment Gift Card'
    (8, 8),  -- 'Hotel' associated with '$300 Wellness Spa Package'
    (9, 9),  -- 'Fitness' associated with '$100 Hotel Stay Gift Certificate'
    (10, 10),-- 'Home' associated with '$200 Fitness Club Membership Voucher'
    (11, 11),-- 'Gaming' associated with '$150 Shopping Mall Gift Card'
    (12, 12);-- 'Books' associated with '$75 Beauty Salon Gift Voucher'

