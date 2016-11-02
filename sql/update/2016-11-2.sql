-- Global Lock--
CREATE TABLE `lock_entity` (
  `id` VARCHAR(50) NOT NULL,
  `owner` VARCHAR(50) NOT NULL,
  `created_date` DATETIME NOT NULL,
  `timedue` DATETIME NOT NULL,
  PRIMARY KEY (`id`));
  
CREATE TABLE `global_task_trigger_context` (
  `id` VARCHAR(50) NOT NULL,
  `last_scheduled_execution_dt` DATETIME NULL,
  `last_actual_execution_dt` DATETIME NULL,
  `last_completion_dt` DATETIME NULL,
  `next_execution_dt` DATETIME NULL,
  PRIMARY KEY (`id`));