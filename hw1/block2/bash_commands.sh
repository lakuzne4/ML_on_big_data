### Список bash-команд по второму блоку

hdfs dfs -mkdir /mainfolder # создать папку
hdfs dfs -mkdir /mainfolder/sub_folder # создать подпапку
# hdfs dfs -rm -skipTrash <file> удалить без отправки в Trash
hdfs dfs -touchz /main_folder/sub_folder/empty.txt # создать пустой файл
hdfs dfs -rm -skipTrash /main_folder/sub_folder/empty.txt # удалить пустой файл
hdfs dfs -rm -R /main_folder # удалить папку
hdfs dfs -mkdir /some_folder # создать новую папку
hdfs dfs -copyFromLocal /my_local_file.txt /some_folder/my_local_file.txt # скопировать из локальной папки
hdfs dfs -cat /some_folder/my_local_file # показать содержимое файла
hdfs dfs -tail /some_folder/my_local_file.txt # показать последние строки файла
hdfs dfs -head /some_folder/my_local_file.txt # показать первые строки файла
hdfs dfs -cp /some_folder/my_local_file.txt /my_local_file.txt # скопировать файл из одной локации в другую
hdfs dfs -setrep -w 2 /my_local_file.txt # изменить фактор репликации файла
hdfs fsck /my_local_file.txt # посмотреть информацию о блоках конкретного файла
hdfs fsck -blockId blk_1073741838_1014.meta # посмотреть информацию о конкретном блоке

