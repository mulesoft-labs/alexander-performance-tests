import subprocess

from src.reader.JsonReader import JsonReader

bash_command = "wrk2 -t{} -c{} -d{}s -R{} -s{} --latency {}"
scaling_ratio = 1

json_reader = JsonReader()

json = json_reader.read("src/resources/config.json")
output_file_dir = json['output_file_dir']
endpoints = json['endpoints']

for endpoint in endpoints:
    bash_command = bash_command.format(endpoint['threads'],
                                       endpoint['connections'],
                                       endpoint['duration'],
                                       endpoint['requests_per_second'],
                                       endpoint['lua_dir'],
                                       endpoint['uri'])

process = subprocess.Popen(bash_command.split(), stdout=subprocess.PIPE)
output, error = process.communicate()

decoded_output = output.decode("utf-8")

with open(output_file_dir, 'wb') as the_file:
    the_file.write(output)
