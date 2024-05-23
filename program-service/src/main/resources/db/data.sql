INSERT INTO program (program.program_id, member_id, program_title, program_type, program_target_value,
                     program_set_count, program_duration,
                     is_favorite, program_usage_count, is_deleted)
VALUES (1, 1, '인터벌 1', 'I', -1, 2, 30, true, 10, false),
       (2, 1, '인터벌 2', 'I', -1, 3, 20, false, 5, false),
       (3, 1, '거리목표 1', 'D', 20, -1, -1, false, 1, false),
       (4, 1, '인터벌 3', 'I', -1, 3, 40, true, 10, false),
       (5, 1, '인터벌 4', 'I', -1, 4, 30, false, 5, false),
       (6, 1, '거리목표 2', 'D', 15, -1, -1, true, 1, false),
       (7, 1, '시간목표 1', 'T', 40, -1, -1, true, 1, false);

INSERT INTO interval_range (program_id, is_running, range_time, range_speed)
VALUES (1, true, 300, 5),
       (1, false, 180, 3),
       (1, true, 360, 8),
       (2, true, 100, 8),
       (2, false, 200, 3),
       (2, true, 300, 10),
       (4, true, 300, 5),
       (4, false, 180, 3),
       (4, true, 360, 8),
       (5, true, 100, 8),
       (5, false, 200, 3),
       (5, true, 300, 10);;