import subprocess
from conf import configure


def check_dir(dir_name):
    args=['-test', '-d', '%s%s'%(configure.HDFSROOT, dir_name)]
    return execute_hdfs_cmd(*args).returncode


def mkdir(dir_name):
    args = ['-mkdir', '-p', '%s%s'%(configure.HDFSROOT, dir_name)]
    assert execute_hdfs_cmd(*args).returncode == 0


def check_file(file_name):
    args = ['-test', '-e', '%s%s'%(configure.HDFSROOT, file_name)]
    return execute_hdfs_cmd(*args).returncode


def upload_file(local_file, hdfs_dir):
    args = ['-put']
    if type(local_file) is list:
        args.extend(local_file).append(hdfs_dir)
    else:
        args.extend([local_file, hdfs_dir])
    return execute_hdfs_cmd(*args).returncode

def rm_file(file):
    args = ['-rm', file]
    return execute_hdfs_cmd(*args)

def rm_dir(dir):
    args = ['-rm', '-r', dir]
    return execute_hdfs_cmd(*args)


def execute_hdfs_cmd(*args):
    cmd = "hadoop fs %s" % " ".join(args)
    print(cmd)
    return subprocess.run(cmd, shell=True, stdout=subprocess.PIPE)


def execute_map_reduce(jar, main_class,input_path, output_path, *args):
    cmd = [configure.HADOOP_PATH, "jar", jar, main_class, input_path, output_path]
    cmd.extend(args)
    cmd=" ".join(cmd)
    print(cmd)
    sts = subprocess.run(cmd, shell=True, stdout=subprocess.PIPE)
    if sts.returncode != 0:
        print(sts.returncode)


if __name__ == '__main__':
    pass
