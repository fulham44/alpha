Для корректной работы тестов необходимо установить lombok.

В классе InsuranceService реализована логика заглушки.

В классе InsuranceResourceApplicationTests проверяется работа со страховкой для детей от 5 лет до 18
(18 не входит в диапозон проверок, так как уже человек совершеннолетний), а именно
добавление, получение и сохранение страховок.

В проекте нет классичеких Assert'ов, так как для реализации проверок достаточно функций из Mockito,
что избавляет класс с тестами от лишнего кода.