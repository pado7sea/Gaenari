INSERT INTO program (member_id, program_title, program_type, program_target_value, program_set_count, program_duration, is_favorite, program_usage_count, is_deleted) VALUES
                                                                                                                                                            (1, '인터벌 1', 'I', -1, 2, 30, true, 10, false),
                                                                                                                                                            (2, '인터벌 2', 'I', -1, 3, 20, false, 5, false),
                                                                                                                                                            (2, '거리목표 프로그램 1', 'D', 20, -1, -1, false, 1, false);

INSERT INTO interval_range (range_id, program_id, is_running, range_time, range_speed) VALUES
                                                                                          (1, 1, true, 300, 5),
                                                                                          (2, 1, false, 180, 3),
                                                                                          (3, 1, true, 360, 8),
                                                                                          (4, 2, true, 100, 8),
                                                                                          (5, 2, false, 200, 3),
                                                                                          (6, 2, true, 300, 10);