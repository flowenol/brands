<?php
// bootstrap.php
use Doctrine\ORM\Tools\Setup;
use Doctrine\ORM\EntityManager;

require_once "vendor/autoload.php";

// Create a simple "default" Doctrine ORM configuration for Annotations
$isDevMode = true;
$config = Setup::createAnnotationMetadataConfiguration(array(__DIR__."/models"), $isDevMode);

// database configuration parameters
$conn = array(
    'driver' => 'pdo_mysql',
    'user'     => 'root',
    'password' => 'root',
    'dbname'   => 'brand',
	'port' => '3306',
	//'unix_socket' => '/Applications/MAMP/tmp/mysql/mysql.sock'
);

// obtaining the entity manager
$entityManager = EntityManager::create($conn, $config);

?>