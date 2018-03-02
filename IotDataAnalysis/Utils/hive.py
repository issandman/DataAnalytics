import subprocess


def execute_hql(hql):
    cmd = ["hive", '-e', hql]
    print(" ".join(cmd))
    sts = subprocess.run(cmd, stdout=subprocess.PIPE)
    if sts.returncode != 0:
        print(sts.returncode)

