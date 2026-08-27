import subprocess

mysql_bin = r"C:\Program Files\MySQL\MySQL Server 8.0\bin\mysql.exe"

def check_mysql():
    commands = [
        "USE codeclash_db; SHOW TABLES;",
        "USE codeclash_db; DESCRIBE users;",
        "USE codeclash_db; SELECT id, username, email, rating, matches_played, wins, losses FROM users;",
        "USE codeclash_db; SELECT id, title, difficulty, time_limit_seconds FROM problems;",
        "USE codeclash_db; SELECT id, room_code, status, host_user_id, guest_user_id FROM rooms LIMIT 5;",
        "USE codeclash_db; SELECT id, room_id, problem_id, status, winner_user_id FROM matches LIMIT 5;"
    ]
    
    for cmd in commands:
        print(f"\n==================== [ {cmd} ] ====================")
        try:
            res = subprocess.run(
                [mysql_bin, "-u", "root", "-pKrish@718", "-e", cmd],
                capture_output=True,
                text=True
            )
            if res.stdout:
                print(res.stdout)
            if res.stderr:
                print("Notice/Error:", res.stderr.strip())
        except Exception as e:
            print("Error running command:", e)

if __name__ == "__main__":
    check_mysql()
