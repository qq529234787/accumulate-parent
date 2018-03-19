::echo %webroot_filepath%
::echo %webroot_filepath%main\webapp\static

pushd %webroot_filepath%main\webapp\static
rd /s /q %webroot_filepath%main\webapp\statictarget
fis release -opm -d %webroot_filepath%main\webapp\statictarget\js


